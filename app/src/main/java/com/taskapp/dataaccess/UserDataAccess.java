package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
// Userをインポート
import com.taskapp.model.User;
import java.io.IOException;

public class UserDataAccess {
    private final String filePath;

    public UserDataAccess() {
        filePath = "app/src/main/resources/users.csv";
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * 
     * @param filePath
     */
    public UserDataAccess(String filePath) {
        this.filePath = filePath;
    }

    /**
     * メールアドレスとパスワードを基にユーザーデータを探します。
     * 
     * @param email    メールアドレス
     * @param password パスワード
     * @return 見つかったユーザー
     */
    public User findByEmailAndPassword(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String t;
            User user;
            reader.readLine();
            while ((t = reader.readLine()) != null) {
                String[] a = t.split(",");
                if (a.length != 4)
                    continue;
                if (a[2].equals(email) && a[3].equals(password)) {
                    user = new User(Integer.parseInt(a[0]), a[1], a[2], a[3]);
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            ;
        }
        return null;
    }

    /**
     * コードを基にユーザーデータを取得します。
     * 
     * @param code 取得するユーザーのコード
     * @return 見つかったユーザー
     */
    public User findByCode(int code) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String t;
            User user;
            reader.readLine();
            while ((t = reader.readLine()) != null) {
                String[] a = t.split(",");
                if (a.length != 4)
                    continue;
                if (Integer.parseInt(a[0]) == code) {
                    user = new User(Integer.parseInt(a[0]), a[1], a[2], a[3]);
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
