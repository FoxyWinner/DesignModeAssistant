package com.designmodeassistant;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.designmodeassistant.adapters.ContentItemRecyclerViewAdapter;
import com.designmodeassistant.httputil.Info;
import com.designmodeassistant.httputil.UIHelper;
import com.designmodeassistant.vo.ContentItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static com.designmodeassistant.httputil.HttpUtils.sendPostMethod;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class ContentFragment extends Fragment
{
    private RecyclerView recyclerView;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String FROM_WHERE = "fromWhere";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String fromWhere="ALL";
    private ContentItemRecyclerViewAdapter.OnItemClickLitener mItemClickListener;
    private static final String TAG = "ContentFragment";

    private View view;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContentFragment()
    {
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ContentFragment newInstance(int columnCount,String fromWhere)
    {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(FROM_WHERE, fromWhere);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            fromWhere = getArguments().getString(FROM_WHERE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // Set the adapter

        if (view instanceof RecyclerView)
        {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
//             添加分割线
//            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
//                    DividerItemDecoration.VERTICAL));
            if (mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else
            {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mItemClickListener = new ContentItemRecyclerViewAdapter.OnItemClickLitener()
            {

                @Override
                public void onItemClick(View view, int position)
                {
                    DetailsFragment detailsFragment= DetailsFragment.newInstance(view.getTag().toString(),fromWhere);
                    FragmentManager fragmentManager =getFragmentManager();
                    FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.fragment_container,detailsFragment);
                    fragmentTransaction.addToBackStack("content");
                    fragmentTransaction.commit();
                }

                @Override
                public void onItemLongClick(View view, int position)
                {
                    Toast.makeText(getActivity(),"[彩蛋]轻点我就好！",
                            Toast.LENGTH_SHORT).show();
                }
            };
            new queryAsyncTask().execute(Info.path_queryContentItem);


        }
        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener)
//        {
//            mListener = (OnListFragmentInteractionListener) context;
//        }
//        else
//        {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mItemClickListener = null;
    }


    class queryAsyncTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            List<ContentItem> contentItemList = new ArrayList<>();
            System.out.println(strings[0]+"异步加载");

            return sendPostMethod(strings[0], "utf-8");
        }
        @Override
        protected void onPostExecute(String result)
        {

            List<ContentItem> items;
            super.onPostExecute(result);
            if (result==null||result.equals(""))
            {
                Log.w(TAG,"进入result查询失败分支");
                //当没有数据时，添加10条假数据
                items = new ArrayList<>();
                for (int i = 0; i < 10 ; i++)
                {
                    items.add(new ContentItem(0,"无数据"));
                }
                Snackbar sb = Snackbar.make(view, "连接服务器失败，无数据", Snackbar.LENGTH_LONG);
                sb .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                sb.show();
            }
            else
            {
                Log.i(TAG,"进入result不为空分支");
                //JSON解析
                items = JSONMyContentItemsData(result);
                Snackbar sb = Snackbar.make(view, "数据查询成功", Snackbar.LENGTH_LONG);
                sb .setAction("确定", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                    }
                });
                sb.show();
            }
            recyclerView.setAdapter(new ContentItemRecyclerViewAdapter(items, mItemClickListener));
            new UIHelper().hideDialogForLoading();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            new UIHelper().showDialogForLoading(getActivity(), "正在加载...", true);
        }
    }
    private List<ContentItem> JSONMyContentItemsData(String result)
    {

        List<ContentItem> contentItems = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(result);
            JSONObject jsonObject;

            Log.i(TAG,"array.length"+array.length());

            for (int i = 0; i < array.length(); i++)
            {
                jsonObject = array.getJSONObject(i);
                ContentItem item = new ContentItem
                        (jsonObject.getInt("itemChapterId"),
                         jsonObject.getString("itemChapterName")
                        );
//                System.out.println("json解析"+item.toString());
                contentItems.add(item);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contentItems;
    }
}
