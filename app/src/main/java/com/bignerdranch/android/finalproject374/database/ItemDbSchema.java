package com.bignerdranch.android.finalproject374.database;

/**
 * Created by meghanhogan on 11/29/16.
 */

public class ItemDbSchema {

    public static final class ItemTable{
        public static final String NAME = "items";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String Name = "name";
            public static final String Status = "status";
            public static final String Price = "price";

        }
    }

    public static final class MemberTable{
        public static final String NAME = "members";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String Name = "name";
            public static final String Number = "number";

        }
    }
}
