import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calc extends Application {
    private double total = 0.0;
    private int num = 1;
    private String num1 = "";
    private String num2 = "";
    private String sign = "";

    private Scene standard;
    private Scene scene;
    private Scene quadratic;

    private Stage stage;

    private Text disp = new Text("_");
    private Text sDisp = new Text();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        //stage.initStyle(StageStyle.UTILITY);

        stage.setTitle("Calculator");
        stage.getIcons().add(new Image("calc.png"));

        createMenuLayout();

        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
    }

    public void createMenuLayout() {
        StackPane root = new StackPane();
        scene = new Scene(root, 350, 350);
        VBox panel = new VBox(10);

        Button stanCalc = new Button("Standard Calculator");
        stanCalc.setOnAction(event -> {
            createStanLayout();
            stage.setScene(standard);
        });

        Button quadCalc = new Button("Quadratic Formula");
        quadCalc.setOnAction(event -> {
            createQuadLayout();
            stage.setScene(quadratic);
        });

        panel.setAlignment(Pos.CENTER);
        panel.getChildren().addAll(stanCalc, quadCalc);
        root.getChildren().addAll(panel);
    }

    public void createStanLayout() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        standard = new Scene(root, 230, 400);

        sDisp.setFont(new Font(15));
        root.getChildren().add(sDisp);

        disp.setFont(new Font(36));
        disp.setTextAlignment(TextAlignment.RIGHT);
        root.getChildren().add(disp);

        //buttons (0-9, -+/*, =, +/- negative, C, CE, sqrt, ^)
        Button zero, one, two, three, four, five, six, seven, eight, nine, subtract, add, divide, multiply, equals, c, ce, sqrt, exp, decimal, negative, menu;

        zero = new Button("0");
        one = new Button("1");
        two = new Button("2");
        three = new Button("3");
        four = new Button("4");
        five = new Button("5");
        six = new Button("6");
        seven = new Button("7");
        eight = new Button("8");
        nine = new Button("9");
        subtract = new Button("-");
        add = new Button("+");
        divide= new Button("/");
        multiply = new Button("*");
        equals = new Button("=");
        c = new Button("C");
        ce = new Button("CE");
        sqrt = new Button("√");
        exp = new Button("x^x");
        decimal = new Button(".");
        negative = new Button("+/-");
        menu = new Button("<-"); //back to menu button

        btnActions(zero, one, two, three, four, five, six, seven, eight, nine,
                subtract, add, divide, multiply,
                equals, c, ce, sqrt, exp, decimal, negative);

        setButSize(zero, one, two, three, four, five, six, seven, eight, nine,
                subtract, add, divide, multiply,
                equals, c, ce, sqrt, exp, decimal,
                negative, menu);

        GridPane buttons = new GridPane();
        buttons.setVgap(5);
        buttons.setHgap(5);
        buttons.setAlignment(Pos.CENTER);

        buttons.addRow(0, c, ce, sqrt, exp);
        buttons.addRow(1, add, one, two, three);
        buttons.addRow(2, subtract, four, five, six);
        buttons.addRow(3, divide, seven, eight, nine);
        buttons.addRow(4, multiply, decimal, zero, equals);

        menu.setOnAction(event -> stage.setScene(scene));
        menu.setMinWidth(43);
        buttons.add(negative, 0, 5);
        buttons.add(menu, 3, 5);

        root.getChildren().addAll(buttons);
    }

    public void btnActions(Button...btns) {
        for (Button btn : btns) {
            switch (btn.getText()) {
                case "0":
                    btn.setOnAction(event -> excH(0));
                    break;
                case "1":
                    btn.setOnAction(event -> excH(1));
                    break;
                case "2":
                    btn.setOnAction(event -> excH(2));
                    break;
                case "3":
                    btn.setOnAction(event -> excH(3));
                    break;
                case "4":
                    btn.setOnAction(event -> excH(4));
                    break;
                case "5":
                    btn.setOnAction(event -> excH(5));
                    break;
                case "6":
                    btn.setOnAction(event -> excH(6));
                    break;
                case "7":
                    btn.setOnAction(event -> excH(7));
                    break;
                case "8":
                    btn.setOnAction(event -> excH(8));
                    break;
                case "9":
                    btn.setOnAction(event -> excH(9));
                    break;
                case "C":
                    btn.setOnAction(event -> {
                        num1 = "";
                        num2 = "";
                        sign = "";
                        disp.setText("_");
                        sDisp.setText("");
                    });
                    num = 1;
                    break;
                case "CE":
                    btn.setOnAction(event -> {
                        if (num == 1) {
                            num1 = "";
                        } else {
                            num2 = "";
                        }
                        disp.setText("_");
                    });
                    break;
                case ".":
                    btn.setOnAction(event -> excH(-1));
                    break;
                case "+":
                    btn.setOnAction(event -> {
                        sign("+");
                    });
                    break;
                case "-":
                    btn.setOnAction(event -> {
                        sign("-");
                    });
                    break;
                case "*":
                    btn.setOnAction(event -> {
                        sign("*");
                    });
                    break;
                case "/":
                    btn.setOnAction(event -> {
                        sign("/");
                    });
                    break;
                case "√":
                    btn.setOnAction(event -> {
                        String dText;
                        if (num == 1) {
                            dText = String.format("√%.4s = %.4f", num1.isEmpty() ? "0" : num1, Math.sqrt(Double.parseDouble(num1.isEmpty() ? "0" : num1)));
                            disp.setText(dText);
                            //"√" + (num1.isEmpty() ? "0" : num1) + " = " + Math.sqrt(Double.parseDouble(num1.isEmpty() ? "0" : num1))
                            num1 = "" + Math.sqrt(Double.parseDouble(num1.isEmpty() ? "0" : num1));
                        } else {
                            dText = String.format("√%.4s = %.4f", num2.isEmpty() ? "0" : num2, Math.sqrt(Double.parseDouble(num2.isEmpty() ? "0" : num2)));
                            disp.setText(dText);
                            //disp.setText("√" + (num2.isEmpty() ? "0" : num2) + " = " + Math.sqrt(Double.parseDouble(num2.isEmpty() ? "0" : num2)));
                            num2 = "" + Math.sqrt(Double.parseDouble(num2.isEmpty() ? "0" : num2));
                        }
                    });
                    break;
                case "x^x" :
                    btn.setOnAction(event -> {
                        sign = "^";
                        num = 2;
                    });
                    break;
                case "+/-":
                    btn.setOnAction(event -> {
                        double temp;
                        if (num == 1) {
                            temp = Double.parseDouble(num1.isEmpty() ? "0" : num1);
                            if (temp != 0) num1 = (-1 * temp) + "";
                            disp.setText("" + num1);
                        } else {
                            temp = Double.parseDouble(num2.isEmpty() ? "0" : num2);
                            if (temp != 0) num2 = (-1 * temp) + "";
                            disp.setText("" + num2);
                        }
                    });
                    break;
                case "=":
                    btn.setOnAction(event -> {
                        num = 1;
                        equ(sign);
                        sign = "";
                    });
                    break;
                default :
                    System.out.printf("Button (%s) does not have an action\n", btn.getText());
            }
        }
    }

    public void sign(String sign) {
        sDisp.setText(num1 + " " + sign);
        if (num == 2) {
            equ(this.sign);
        } else {
            num = 2;
        }
        this.sign = sign;
    }

    public void equ(String sign) {
        switch (sign) {
            case "+":
                if (num1.isEmpty()) { num1 = "0"; }
                if (num2.isEmpty()) { num2 = "0"; }
                total = Double.parseDouble(num1) + Double.parseDouble(num2);
                equH();
                break;
            case "-":
                if (num1.isEmpty()) { num1 = "0"; }
                if (num2.isEmpty()) { num2 = "0"; }
                total = Double.parseDouble(num1) - Double.parseDouble(num2);
                equH();
                break;
            case "/":
                if (num1.isEmpty()) { num1 = "0"; }
                if (num2.isEmpty()) { num2 = "0"; }
                total = Double.parseDouble(num1) / Double.parseDouble(num2);
                equH();
                break;
            case "*":
                if (num1.isEmpty()) { num1 = "0"; }
                if (num2.isEmpty()) { num2 = "0"; }
                total = Double.parseDouble(num1) * Double.parseDouble(num2);
                equH();
                break;
            case "^":
                if (num1.isEmpty()) { num1 = "0"; }
                if (num2.isEmpty()) { num2 = "0"; }
                total = Math.pow(Double.parseDouble(num1), Double.parseDouble(num2));
                equH();
                break;
            default :
                System.out.println("Default equals");
                break;
        }
    }

    public void equH() {
        String dText;
        sDisp.setText(num1 + " " + sign + " " + num2 + " = ");
        dText = String.format("%.4f", total);
        disp.setText(dText);
        num1 = "" + total;
        num2 = "";
    }

    public void excH(int digit) {
        try {
            exc(digit);
        } catch (NumberTooLongException ntle) {
            System.out.println(ntle.getMessage());
        } catch (TooManyDecimalsException tmde) {
            System.out.println(tmde.getMessage());
        }
    }

    public void exc(int digit) {
        if (num == 1) {
            if (num1.length() > 9) {
                throw new NumberTooLongException("Character limit of 10 cannot be exceeded.");
            } else {
                if (digit == -1) {
                    if (num1.contains(".")) {
                        throw new TooManyDecimalsException("Invalid number of decimals entered.");
                    }
                    num1 += ".";
                } else {
                    num1 += digit;
                }
                disp.setText(num1);
            }
        } else {
            if (num2.length() > 9) {
                throw new NumberTooLongException("Character limit of 10 cannot be exceeded.");
            } else {
                if (digit == -1) {
                    if (num2.contains(".")) {
                        throw new TooManyDecimalsException("Invalid number of decimals entered.");
                    }
                    num2 += ".";
                } else {
                    num2 += digit;
                }
                disp.setText(num2);
            }
        }
    }

    public void setButSize(Button...btns) {
        for (Button btn : btns) {
            btn.setMinWidth(43);
            btn.setMinHeight(43);
            btn.setFont(new Font(15));
        }
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void createQuadLayout() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        quadratic = new Scene(root, 350, 350);

        //result
        Text result = new Text();
        result.setTextAlignment(TextAlignment.CENTER);
        //result.setStyle("-fx-text-fill: #5a0505;");
        result.setFont(new Font("Times New Roman", 25));

        //input
        HBox input = new HBox(5);
        TextField aField = new TextField("A");
        aField.setMaxWidth(75);
        TextField bField = new TextField("B");
        bField.setMaxWidth(75);
        TextField cField = new TextField("C");
        cField.setMaxWidth(75);
        input.getChildren().addAll(aField, bField, cField);
        input.setAlignment(Pos.CENTER);

        root.getChildren().add(input);

        //buttons
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button calc = new Button("CALCULATE"); //calculate button
        calc.setTextFill(Color.ALICEBLUE);
        calc.setStyle("-fx-font: 13 arial; -fx-base: #c6600a;");
        calc.setOnAction(event -> {
            try {
                Double a = Double.parseDouble(aField.getText());
                Double b = Double.parseDouble(bField.getText());
                Double c = Double.parseDouble(cField.getText());

                double twoA = 2 * a;
                double inner = Math.pow(b, 2) - (4 * a * c);
                if (inner < 0) {
                    inner *= -1;
                    String p = String.format("%.2f + %.2fi", (-b) / twoA, Math.sqrt(inner) / twoA);
                    String m = String.format("%.2f - %.2fi", (-b) / twoA, Math.sqrt(inner) / twoA);
                    result.setText(String.format("Result:\n%s\n%s", p, m));
                } else {
                    double sqrt = Math.sqrt(inner);
                    double plusTop = (-b) + sqrt;
                    double minusTop = (-b) - sqrt;
                    double plus = plusTop / twoA;
                    double minus = minusTop / twoA;

                    result.setText(String.format("Result:\n%.2f\n%.2f", plus, minus));
                }


            } catch (NumberFormatException nfe) {
                result.setWrappingWidth(quadratic.getWidth() - 20);
                result.setText("At least one of your inputs was not a number.");
            } catch (Exception e) {
                result.setText("An exception was caught.");
            }
        });

        Button menu = new Button("Menu"); //back to menu button
        menu.setOnAction(event -> stage.setScene(scene));

        buttons.getChildren().addAll(calc, menu);
        root.getChildren().addAll(buttons, result);

        root.setSpacing(10);
    }
}