package eliran.taptheball.com.taptheball;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

Button StartBtn;
    TextView RecordTV;
    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_start, container, false);
        RecordTV= (TextView) view.findViewById(R.id.RecordTV);
        StartBtn= (Button) view.findViewById(R.id.StartBtn);
        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("eliran.taptheball.com.taptheball.GAME_BEGIN");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                getActivity().getFragmentManager().beginTransaction().remove(StartFragment.this).commit();
            }
        });
        return view;
    }

}
