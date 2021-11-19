package com.bidamcat.tp11kakaoplacesearchapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //카카오 키해시를 얻어오기
        String keyHash= Utility.INSTANCE.getKeyHash(this);
        Log.i("keyhash", keyHash);

    }

    public void clickGo(View view) {
        //MainActivity로 이동
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickSignUp(View view) {
        //회원가입 화면(액티비티)로 이동
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void clickLoginEmail(View view) {
        //이메일 로그인 화면(액티비티)로 이동
    }

    public void clickLoginKakao(View view) {
        
        //카카오계정 로그인 요청
        UserApiClient.getInstance().loginWithKakaoAccount(this, new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken!=null){
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    //로그인 사용자 정보 얻어오기
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            if(user!=null){
                                String userid= user.getId()+"";
                                String email= user.getKakaoAccount().getEmail();

                                //다른 액티비티에서 마음대로 쓸 수 있도록..
                                G.user= new UserAccount(userid, email);

                                //new AlertDialog.Builder(LoginActivity.this).setMessage(email+"").show();
                                //Main화면 이동
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                            return null;
                        }
                    });
                }
                return null;
            }
        });

    }

    public void clickLoginGoogle(View view) {
        // 구글 개발자 콘솔에서 사용자 인증 키 등록 - oAuth [패키지명, SHA-1등록]
        //구글 로그인 SDK 라이브러리 추가 - play-services-auth

        //구글 로그인으로 받아올 정보 옵션 [ id, email 요청 ]
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId().requestEmail().build();
        
        GoogleSignInClient googleClient= GoogleSignIn.getClient(this, gso);
        //구글 로그인 화면(액티비티)를 실행하는 Intent객체
        Intent intent= googleClient.getSignInIntent();
        startActivityForResult(intent, 100);
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode==100){
            try {
                GoogleSignInAccount account= GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                
                if (account!=null){
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    String userid= account.getId();
                    String email= account.getEmail();

                    //new AlertDialog.Builder(LoginActivity.this).setMessage(email+"").show();
                    G.user= new UserAccount(userid, email);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickLoginNaver(View view) {

        //주의!네이버로그인은 현재는(2021.11.18) 타겟버전이 30버전 까지 가능함

        //1. 네이버 로그인 인스턴스초기화
        OAuthLogin oAuthLogin= OAuthLogin.getInstance();
        oAuthLogin.init(this, "Vsotz_uAfD6tFU_mJGT5","UDSY2DCgGF","써플");

        //2. 개발자 사이트에서 앱등록 - 패키지명.. 클라이언트ID, Secret 번호 받기

        //3. 로그인 버튼 구현 방법 2가지
        //3.1) 네이버로그인버튼 전용뷰 이용 - 이버튼은 클릭이벤트 코드를 직접작성하지 않아도 자동 로그인동작됨
        //3.2) startOAuthLoginActivity()메소드로 로그인 구현(커스텀 버튼 모양일때 사용)
        oAuthLogin.startOauthLoginActivity(this, new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                //파라미터가 로그인 성공여부를 전달받음.
                if(success){
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}