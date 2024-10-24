package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.taskapp.model.Task;
import com.taskapp.model.User;

public class TaskDataAccess {

    private final String filePath;

    private final UserDataAccess userDataAccess;

    public TaskDataAccess() {
        filePath = "app/src/main/resources/tasks.csv";
        userDataAccess = new UserDataAccess();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * 
     * @param filePath
     * @param userDataAccess
     */
    public TaskDataAccess(String filePath, UserDataAccess userDataAccess) {
        this.filePath = filePath;
        this.userDataAccess = userDataAccess;
    }

    /**
     * CSVから全てのタスクデータを取得します。
     *
     * @see com.taskapp.dataaccess.UserDataAccess#findByCode(int)
     * @return タスクのリスト
     */
    public List<Task> findAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<Task> tasks = new ArrayList<>();
            String t;
            reader.readLine();
            while ((t = reader.readLine()) != null) {
                String[] a = t.split(",");
                if (a.length != 4)
                    continue;
                int code = Integer.parseInt(a[0]);
                String name = a[1];
                int status = Integer.parseInt(a[2]);
                User repUser = userDataAccess.findByCode(Integer.parseInt(a[3]));
                Task task = new Task(code, name, status, repUser);
                tasks.add(task);
            }
            return tasks;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * タスクをCSVに保存します。
     *
     * @param task 保存するタスク
     */
    public void save(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("\n");
            writer.write(createLine(task));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * コードを基にタスクデータを1件取得します。
     * 
     * @param code 取得するタスクのコード
     * @return 取得したタスク
     */
    public Task findByCode(int code) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Task task;
            String t;
            reader.readLine();
            while ((t = reader.readLine()) != null) {
                String[] a = t.split(",");
                if (a.length != 4)
                    continue;
                int taskCode = Integer.parseInt(a[0]);
                if (taskCode == code) {
                    String name = a[1];
                    int status = Integer.parseInt(a[2]);
                    User repUser = userDataAccess.findByCode(Integer.parseInt(a[3]));
                    task = new Task(taskCode, name, status, repUser);
                    return task;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * タスクデータを更新します。
     * 
     * @param updateTask 更新するタスク
     */
    public void update(Task updateTask) {
        List<Task> tasks = findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Code,Name,Status,Rep_User_Code");
            for (Task task : tasks) {
                if (task.getCode() == updateTask.getCode()) {
                    writer.write("\n");
                    writer.write(createLine(updateTask));
                    continue;
                }
                writer.write("\n");
                writer.write(createLine(task));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * コードを基にタスクデータを削除します。
     * 
     * @param code 削除するタスクのコード
     */
    // public void delete(int code) {
    // try () {

    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    /**
     * タスクデータをCSVに書き込むためのフォーマットを作成します。
     * 
     * @param task フォーマットを作成するタスク
     * @return CSVに書き込むためのフォーマット文字列
     */
    private String createLine(Task task) {
        return task.getCode() + "," + task.getName() + "," + task.getStatus() + "," + task.getRepUser().getCode();
    }
}