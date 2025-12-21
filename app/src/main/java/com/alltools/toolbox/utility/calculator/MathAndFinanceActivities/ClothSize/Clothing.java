package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ClothSize;

import androidx.exifinterface.media.ExifInterface;
import androidx.room.RoomMasterTable;

public interface Clothing {
    public static final String[] MEN_CLOTHING_US = {"30", "32", "34", "36", "38", "40", RoomMasterTable.DEFAULT_ID, "44", "46", "48", "50"};
    public static final String[] MEN_CLOTHING_UK = {"30", "32", "34", "36", "38", "40", RoomMasterTable.DEFAULT_ID, "44", "46", "48", "50"};
    public static final String[] MEN_CLOTHING_FR = {"40", RoomMasterTable.DEFAULT_ID, "44", "46", "48", "50", "52", "54", "56", "58", "60"};
    public static final String[] MEN_CLOTHING_IT = {"40", RoomMasterTable.DEFAULT_ID, "44", "46", "48", "50", "52", "54", "56", "58", "60"};
    public static final String[] MEN_CLOTHING_SML = {"XXS", "XS", "S", "S", "M", "M", "L", "L", "XL", "XXL", "XXXL"};
    public static final String[] WOMEN_CLOTHING_US = {"0", ExifInterface.GPS_MEASUREMENT_2D, "4", "6", "8", "10", "12", "14", "16", "18", "20"};
    public static final String[] WOMEN_CLOTHING_UK = {ExifInterface.GPS_MEASUREMENT_2D, "4", "6", "8", "10", "12", "14", "16", "18", "20", "22"};
    public static final String[] WOMEN_CLOTHING_FR = {"32", "34", "36", "38", "40", RoomMasterTable.DEFAULT_ID, "44", "46", "48", "50", "52"};
    public static final String[] WOMEN_CLOTHING_DE = {"28", "30", "32", "34", "36", "38", "40", RoomMasterTable.DEFAULT_ID, "44", "46", "48"};
    public static final String[] WOMEN_CLOTHING_SML = {"XS", "S", "S", "M", "M", "L", "L", "XL", "XL", "XXL", "XXXL"};
}