package com.designmodeassistant;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.designmodeassistant.httputil.Info;
import com.designmodeassistant.httputil.UIHelper;
import com.designmodeassistant.vo.DesignMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

import static com.designmodeassistant.httputil.HttpUtils.sendPostMethod;

/**
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CHAPTER_ID = "chapterId";
    private static final String SHOW_WHAT = "showWhat";
    private static final String TAG = "DetailsFragment";

    private String chapterId;
    private String showWhat;

    private LinearLayout ll;


    private View view;

    public DetailsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param chapterId Parameter 1.
     *  @param showWhat Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String chapterId,String showWhat)
    {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(CHAPTER_ID, chapterId);
        args.putString(SHOW_WHAT, showWhat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            showWhat = getArguments().getString(SHOW_WHAT);
            chapterId = getArguments().getString(CHAPTER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        ll = (LinearLayout) view.findViewById(R.id.ll_details);

        new queryDesignModeAsyncTask().execute(Info.path_QueryDesignModeBeanById+chapterId);

        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }

    class queryDesignModeAsyncTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            System.out.println(strings[0]+"异步加载");
            return sendPostMethod(strings[0], "utf-8");
        }
        @Override
        protected void onPostExecute(String result)
        {
            DesignMode mode;
            super.onPostExecute(result);
            if (result==null||result.equals(""))
            {
                Log.w(TAG,"进入result查询失败分支");
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
                mode = JSONMyDesignModeData(result);
                initLinearLayout(ll,mode);
                System.out.println(mode.toString());
                Snackbar sb = Snackbar.make(view, "数据查询成功", Snackbar.LENGTH_LONG);
                sb .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                sb.show();
            }
            new UIHelper().hideDialogForLoading();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            new UIHelper().showDialogForLoading(getActivity(), "正在加载...", true);
        }
    }
    private DesignMode JSONMyDesignModeData(String result)
    {
        DesignMode mode=null;
        try {
            JSONArray array = new JSONArray(result);
            JSONObject jsonObject;
            Log.i(TAG,"array.length"+array.length());
            for (int i = 0; i < array.length(); i++)
            {
                jsonObject = array.getJSONObject(i);
                mode = new DesignMode
                        (jsonObject.getInt("itemChapterId"),
                                jsonObject.getString("itemChapterName"),
                                jsonObject.getInt("hasGener"),
                                jsonObject.getInt("hasUml"),
                                jsonObject.getInt("hasCode"),
                                jsonObject.getString("generalize"),
                                jsonObject.getString("umlImageUrl"),
                                jsonObject.getString("code")
                        );
//                System.out.println("json解析"+mode.getCode()+""+mode.getGeneralize());
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mode;
    }

    public void initLinearLayout(LinearLayout ll,DesignMode designMode)
    {
        final TextView tv =new TextView(getActivity());

        LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.WRAP_CONTENT );

        String htmlHeader = "<style>\n" +
                "    .code\n" +
                "    {\n" +
                "        font-size: 16px;\n" +
                "    }\n" +
                "    .normal\n" +
                "    {\n" +
                "        font-size: 20px;\n" +
                "        font-family: \"微软雅黑\";\n" +
                "        white-space: pre-wrap;\n" +
                "        word-wrap: break-word;\n" +
                "    }\n" +
                "    img\n" +
                "    {\n" +
                "        height: auto;\n" +
                "        width:100%;\n" +
                "    }\n" +
                "</style>\n"
                ;
        CodeView codeView =new CodeView(getActivity());

        codeView.setLayoutParams(lp);
        codeView.setTheme(CodeViewTheme.GITHUB).fillColor();

//        根据showWhat 是ALL UML CODE GENER 为用户动态显示不同的Fragment

        if (showWhat.equals("ALL"))
        {
            codeView.showCodeHtmlByClass(designMode.getGeneralize()+designMode.getUmlImageUrl()+designMode.getCode(), "code");
        }
        else if (showWhat.equals("UML"))
        {
//            显示uml,改为其他？
            codeView.showCodeHtmlByClass(htmlHeader+designMode.getUmlImageUrl(), "code");
        }
        else if(showWhat.equals("CODE"))
        {
//            显示code
            codeView.showCodeHtmlByClass(htmlHeader+designMode.getCode(), "code");
        }
        else if (showWhat.equals("GENER"))
        {
//            显示概要
            codeView.showCodeHtmlByClass(htmlHeader+designMode.getGeneralize(), "code");
        }

        ll.addView(codeView);

    }
}
