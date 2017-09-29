package com.hanbit.kakaotalk;

import java.util.ArrayList;

/**
 * Created by 1027 on 2017-09-29.
 */

public class Service {
    public static interface  IPredicate{
        //실행만 하고 할지 말지만 판다하는 로직이다 , predicate lambda를 흉내낸것
        public void execute();
    }
    public static  interface IPost{
        public void execute(Object o);
    }
    public static interface IPut{
        public void execute(Object o);
    }
    public static interface IGet{
        public void execute(Object o);
    }
    public static interface IDelete{
        public void execute(Object o);
    }
    public static interface IList{
        public ArrayList<?>execute(Object o);
    }
}
