package com.gigawattstechnology.waapp;

public class StringEncryptor {
    String text;

    public StringEncryptor(String text) {
        this.text = text;
    }

    public String getText() {
        String encrypt="";
        text = text.replaceAll(" ", "");
        int i, j;
        int L = text.length();
        double val = Math.sqrt(L);
        int row = (int) Math.floor(val);
        int coloumn = (int) Math.ceil(val);
        int left=coloumn-(L%coloumn);
        while(left>0){
            text=text+" ";
            left--;
        }
        while (true) {
            if (row * coloumn >= L) {
                for (i = 0; i < coloumn; i++) {
                    for (j = 0; j < row; j++) {
                        if (text.substring((i + (j * coloumn)),(i + (j * coloumn))+1) != " ") {
                            encrypt = encrypt + text.charAt((i + (j * coloumn)));
                        }
                    }
                    encrypt = encrypt + " ";
                }
                break;
            } else {
                row++;
            }
        }
        return encrypt;
    }
}
