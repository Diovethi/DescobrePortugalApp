package com.example.myapplication;

public class Utils {


    public static int getAvatarIconId( String i) {
        switch (i) {
            case "1":
                return R.drawable.avatar1;
            case "2":
                return R.drawable.avatar2;
            case "3":
                return R.drawable.avatar3;
            case "4":
                return R.drawable.avatar4;
            case "5":
                return R.drawable.avatar5;
            case "6":
                return R.drawable.avatar6;
            case "7":
                return R.drawable.avatar7;
            case "8":
                return R.drawable.avatar8;
            case "9":
                return R.drawable.avatar9;
            case "10":
                return R.drawable.avatar10;
            case "11":
                return R.drawable.avatar11;
            case "12":
                return R.drawable.avatar12;
            case "13":
                return R.drawable.avatar13;
            case "14":
                return R.drawable.avatar14;
            case "15":
                return R.drawable.avatar15;
            case "16":
                return R.drawable.avatar16;
            case "17":
                return R.drawable.avatar17;
            case "18":
                return R.drawable.avatar18;
            default:
                return R.drawable.add_user;
        }
    }
    public static int getColorLightAvatar(String i) {
        switch (i) {
            case "1":
            case "10":
            case "15":
                return R.color.corLightAvatar1;
            case "2":
                return R.color.corLightAvatar2;
            case "3":
            case "6":
            case "8":
            case "12":
            case "13":
            case "14":
            case "16":
                return R.color.corLightAvatar3;
            case "4":
            case "11":
                return R.color.corLightAvatar4;
            case "5":
            case "7":
            case "17":
            case "18":
                return R.color.corLightAvatar6;
            case "9":
                return R.color.corLightAvatar5;
            default:
                return R.color.white;
        }
    }

    public static int getColorDarkAvatar(String i) {
        switch (i) {
            case "1":
            case "10":
            case "15":
                return R.color.corDarkAvatar1;
            case "2":
                return R.color.corDarkAvatar2;
            case "3":
            case "6":
            case "8":
            case "12":
            case "13":
            case "14":
            case "16":
                return R.color.corDarkAvatar3;
            case "4":
            case "11":
                return R.color.corDarkAvatar4;
            case "5":
            case "7":
            case "17":
            case "18":
                return R.color.corDarkAvatar6;
            case "9":
                return R.color.corDarkAvatar5;
            default:
                return R.color.white;
        }
    }


    public static int getBackgroundImage( String i) {
        switch (i) {
            case "Lisboa":
                return R.drawable.lisboa;
            case "Porto":
                return R.drawable.porto;
            case "Castelo Branco":
                return R.drawable.castelobranco;
            case "Fatima":
                return R.drawable.fatima;
            case "Covilha":
                return R.drawable.avatar5;
            case "Fundao":
                return R.drawable.avatar6;
            case "Funchal":
                return R.drawable.avatar7;
            default:
                return R.drawable.lisboa32;
        }
    }


    public static Integer getTrofeuImg(Integer nRespostaCertas)  {

        if(nRespostaCertas<=3)
            return R.drawable.trofeu_bronze_logo;
        else if(nRespostaCertas<=5)
            return R.drawable.trofeu_prata_logo;
        else
            return R.drawable.trofeu_ouro_logo;

    }
}
