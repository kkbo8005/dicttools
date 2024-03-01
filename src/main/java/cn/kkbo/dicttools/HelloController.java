package cn.kkbo.dicttools;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField birthday;

    @FXML
    private TextArea Linkkey;

    @FXML
    private TextArea year;

    @FXML
    private TextField dictlenStart;
    @FXML
    private TextField dictlenEnd;

    @FXML
    private TextField systemName;

    @FXML
    private TextField company;

    @FXML
    private VBox gz2;

    @FXML
    private VBox gz1;

    @FXML
    private TextField qq;

    @FXML
    private VBox gz4;

    @FXML
    private VBox gz3;

    @FXML
    private TextField UserName;

    @FXML
    private ScrollPane gz1scroll;
    @FXML
    private ScrollPane gz3scroll;
    @FXML
    private TextArea Commonphrases;

    @FXML
    private TextField wife;

    @FXML
    private TextField workNumber;

    @FXML
    private TextField phone;

    @FXML
    private CheckBox FistA;

    @FXML
    private TextField idcard;

    @FXML
    private TextField domain;

    @FXML
    private TextField babyName;

    @FXML
    private TextArea dictlist;

    @FXML
    private TextField UserNameEn;

    @FXML
    private TextArea oldAccount;


    @FXML
    private Label status;

    private List<String> str1 = new ArrayList<>();
    private List<String> str2 = new ArrayList<>();
    private List<String> str3 = new ArrayList<>();
    private List<String> str4 = new ArrayList<>();

    private  void GetSelectValue(VBox box,List<String> strlist)
    {
        box.getChildren().forEach(item->{
            CheckBox cbox = (CheckBox) item;
            if(cbox.isSelected()) {
                InitList(strlist,cbox.getText());
            }
        });
    }
    @FXML
    protected void onHelloButtonClick() {

        //清空待生成的字符串书组
        str1.clear();
        str2.clear();
        str3.clear();
        GetSelectValue(gz1,str1);
        GetSelectValue(gz2,str2);
        GetSelectValue(gz3,str3);
        if(str1.isEmpty()&&str2.isEmpty()&&str3.isEmpty())
        {
            AlertDlg("请从密码规则中选择规则以生成密码字典");
            return ;
        }



        status.setText("字典生成中....");


        List<List<String>> dimensionValues = new ArrayList<>();
        if(!str1.isEmpty()) {
            dimensionValues.add(str1);
        }
        if(!str2.isEmpty()) {
            dimensionValues.add(str2);
        }
        if(!str3.isEmpty()) {
            dimensionValues.add(str3);
        }
        List<String> descartes = descartes(dimensionValues, 0, "", new ArrayList<>());
        int i = 0;
        for (String str :descartes) {
            if(str.length()>Integer.parseInt(dictlenStart.getText())&&str.length()<=Integer.parseInt(dictlenEnd.getText())) {
                dictlist.setText(str + "\n" + dictlist.getText());
                i++;
            }
        }
        status.setText("已按以上规则组成生成"+i+"个密码");
    }
    private List<String> descartes(List<List<String>> dimensionValues, int position, String originCode,
                                   List<String> result) {
//      if (CollectionUtils.isEmpty(dimensionValues) || position >= dimensionValues.size()) {
//          return result;
//      }
        // 获取指定行数据
        List<String> rowValue = dimensionValues.get(position);
        for (String code : rowValue) {
            // 第一行不用拼接，直接copy，第二行开始需要在末尾拼接code
            String resultCode = position == 0 ? code : originCode + "" + code;
            // 如果当前位置是最后一行，则可以添加最终resultCode构建结果
            if (position == dimensionValues.size() - 1) {
                result.add(resultCode);
            } else {
                // 否则进入下一行
                descartes(dimensionValues, position + 1, resultCode, result);
            }
        }
        return result;
    }
    @FXML
    void onClearDictClick() {
       dictlist.setText("");
       status.setText("");
    }

    public void AlertDlg(String alertMsg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }

    private void CheckValue(String textboxName,CheckBox cbox)
    {
        switch (textboxName)
        {
            case "姓（英文）":
                if(UserName.getText().isEmpty())
                {
                    AlertDlg("姓(英文）不能位空");
                    UserName.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "名（英文）":
                if(UserNameEn.getText().isEmpty())
                {
                    AlertDlg("名(英文）不能位空");
                    UserNameEn.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "生日":
                if(birthday.getText().isEmpty())
                {
                    AlertDlg("生日不能位空");
                    birthday.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "手机号码":
                if(phone.getText().isEmpty())
                {
                    AlertDlg("手机号码不能位空");
                    phone.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "qq":
                if(qq.getText().isEmpty())
                {
                    AlertDlg("qq号码不能位空");
                    qq.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "身份证后6位":
                if(idcard.getText().isEmpty())
                {
                    AlertDlg("身份证后6位不能位空");
                    idcard.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "配偶姓名":
                if(wife.getText().isEmpty())
                {
                    AlertDlg("配偶姓名不能位空");
                    wife.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "子女姓名":
                if(babyName.getText().isEmpty())
                {
                    AlertDlg("子女姓名不能位空");
                    babyName.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "公司名称":
                if(company.getText().isEmpty())
                {
                    AlertDlg("公司名称不能位空");
                    company.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "工号":
                if(workNumber.getText().isEmpty())
                {
                    AlertDlg("工号不能位空");
                    workNumber.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "域名":
                if(domain.getText().isEmpty())
                {
                    AlertDlg("域名不能位空");
                    domain.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "系统名":
                if(systemName.getText().isEmpty())
                {
                    AlertDlg("系统名不能位空");
                    systemName.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "连接符":
                if(Linkkey.getText().isEmpty())
                {
                    AlertDlg("系统名不能位空");
                    Linkkey.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "年份":
                if(year.getText().isEmpty())
                {
                    AlertDlg("年份不能位空");
                    year.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "常用词组":
                if(Commonphrases.getText().isEmpty())
                {
                    AlertDlg("常用词组不能位空");
                    Commonphrases.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            case "曾用账号":
                if(oldAccount.getText().isEmpty())
                {
                    AlertDlg("曾用账号不能位空");
                    oldAccount.requestFocus();
                    cbox.setSelected(false);
                }
                break;
            default:
                break;
        }
    }
    private void InitList(List<String> strlist,String textboxName)
    {
        switch (textboxName)
        {
            case "姓（英文）":
              strlist.add(UserName.getText());
              break;
            case "名（英文）":
                strlist.add(UserNameEn.getText());
                 break;
            case "生日":
                strlist.add(birthday.getText());
                break;
            case "手机号码":
                strlist.add(phone.getText());
                break;
            case "qq":
                strlist.add(qq.getText());
                break;
            case "身份证后6位":
                strlist.add(idcard.getText());
                break;
            case "配偶姓名":
                strlist.add(wife.getText());
                break;
            case "子女姓名":
                strlist.add(babyName.getText());
                break;
            case "公司名称":
                strlist.add(company.getText());
                break;
            case "工号":
                strlist.add(workNumber.getText());
                break;
            case "域名":
                strlist.add(domain.getText());
                break;
            case "系统名":
                strlist.add(systemName.getText());
                break;
            case "连接符":
                for (String s : Linkkey.getText().split("\n")) {
                    strlist.add(s);
                }
                break;
            case "年份":
                for (String s : year.getText().split("\n")) {
                    strlist.add(s);
                }
                break;
            case "常用词组":
                for (String s : Commonphrases.getText().split("\n")) {
                    strlist.add(s);
                }
                break;
            case "曾用账号":
                for (String s : oldAccount.getText().split("\n")) {
                    strlist.add(s);
                }
                break;
            default:
                break;
        }
    }

    private  void CheckBoxBind(VBox box)
    {
        box.getChildren().forEach(item->{
            CheckBox cbox = (CheckBox)item;
            cbox.setOnAction((event) -> {
                if (cbox.isSelected()) {
                    CheckValue(cbox.getText(),cbox);
                    //System.out.println("选项被选中！");
                } else {
                    //System.out.println("选项未被选中！");
                }
            });
        });
    }
    private  void CheckBoxClearSelect(VBox box)
    {
        box.getChildren().forEach(item->{
            CheckBox cbox = (CheckBox)item;
            if(cbox.isSelected())
            {
                cbox.setSelected(false);
            }
        });
    }
    @FXML
    void initialize() {
        CheckBoxBind(gz1);
        CheckBoxBind(gz2);
        CheckBoxBind(gz3);
        gz1scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        gz3scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @FXML
    void oninitTextClick() {
        UserName.setText("");
        UserNameEn.setText("");
        birthday.setText("");
        phone.setText("");
        qq.setText("");
        idcard.setText("");
        wife.setText("");
        babyName.setText("");
        company.setText("");
        workNumber.setText("");
        domain.setText("");
        systemName.setText("");
        oldAccount.setText("");
        CheckBoxClearSelect(gz1);
        CheckBoxClearSelect(gz2);
        CheckBoxClearSelect(gz3);
        dictlist.setText("");
        status.setText("");
    }
    @FXML
    void onSaveDictClick() {

        if(dictlist.getText().isEmpty())
        {
            AlertDlg("还未生成密码字典！");
        }else {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName("results.txt");
            fileChooser.setTitle("密码字典保存");
            Stage s = new Stage();
            File file = fileChooser.showSaveDialog(s);
            if(file==null) {return;}
            String exportFilePath = file.getAbsolutePath().replaceAll(".txt", "")+".txt";
            //...此处省略客户端向服务器请求，并在服务器端直接下载到本地，建议用流方式，不易内存溢出。

            File savefile = new File(exportFilePath);
            // 指定要保存的文件名及路径
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(savefile.toURI()))) {
                writer.write(dictlist.getText());
                AlertDlg("字典文件保存成功！");
            } catch (Exception e) {
                System.out.println("保存文件出错 ");
            }
        }
    }

}