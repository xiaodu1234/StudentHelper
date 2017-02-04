package activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.schoolnews.R;

import Presenter.MVPInterface;

/**
 * Created by duchaoqiang on 2017/1/5.
 */
public class LoginActivity extends BaseActivity {
    private ImageView barcodeImage;
    private EditText  barcodeInput;
    private EditText  userNameInput;
    private EditText  passwordInput;
    private Button    loginBtn;
    @Override
    public int getView() {
        return R.layout.activity_login;
    }

    @Override
    public int getIndentity() {
        return MVPInterface.LOGIN;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        barcodeImage= (ImageView) findViewById(R.id.barcodeImage);
        barcodeInput= (EditText) findViewById(R.id.barcodeInput);
        userNameInput= (EditText) findViewById(R.id.usernameInput);
        passwordInput= (EditText) findViewById(R.id.passwordInput);
        loginBtn= (Button) findViewById(R.id.submitBtn);
        loginBtn.setOnClickListener(v -> {
            mpresenter.submit(userNameInput.getText().toString().trim(),passwordInput.getText().toString().trim());
        });
        mpresenter.initWithValue(barcodeImage,this);
    }

    @Override
    public void initialValue(Object... args) {
        barcodeImage.setImageBitmap((Bitmap) args[0]);
        barcodeInput.setText((CharSequence) args[1]);
    }

    @Override
    public void goActivity(Object obj) {
        Intent intent=new Intent(this,HomeActivity.class);
        intent.putExtra("cookie",obj.toString());
        startActivity(intent);
        finish();
    }
}
