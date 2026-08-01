package com.tnd.multifuction.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tnd.multifuction.R;
import com.tnd.multifuction.util.Global;

/**
 * 上传网址设置
 */
public class EditURLDialog2 extends Dialog {
    private Context context;
    private TextView tv_title, tv_url, tv_user, tv_pw;
    private EditText et_url, et_user, et_pw;
    private Button btn_save, btn_cancel;
    private View contentView;

    public EditURLDialog2(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }


    private void init() {
        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_url_layout2, null, false);
        et_url = contentView.findViewById(R.id.et_url);
        et_user = contentView.findViewById(R.id.et_user);
        et_pw = contentView.findViewById(R.id.et_pw);
        btn_save = contentView.findViewById(R.id.btn_save);
        btn_cancel = contentView.findViewById(R.id.btn_cancel);
//        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void showDilaog(OnUrlSave2 onUrlSave) {
        this.onUrlSave = onUrlSave;
        if (isShowing()) {
            dismiss();
        }
        et_url.setText(Global.YNM_BaseUrl);
        et_user.setText(Global.YNM_APP_ID);
        et_pw.setText(Global.YNM_APP_PW);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNull()) {
                    if (onUrlSave != null) {
                        onUrlSave.onUrlSave(et_url.getText().toString().trim(),
                                et_user.getText().toString().trim(),
                                et_pw.getText().toString().trim());
                        dismiss();
                    }
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        show();
        setContentView(contentView);
    }

    private boolean isNull() {
        if (et_url.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "请输入网址", Toast.LENGTH_SHORT).show();
            return true;
        } else if (et_user.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "请输入APP ID", Toast.LENGTH_SHORT).show();
            return true;
        } else if (et_pw.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    OnUrlSave2 onUrlSave;

    public void setOnUrlSave(OnUrlSave2 onUrlSave) {
        this.onUrlSave = onUrlSave;
    }

    public interface OnUrlSave2 {
        void onUrlSave(String url, String user, String pw);
    }

}
