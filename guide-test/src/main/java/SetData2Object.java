import java.lang.reflect.Field;

public class SetData2Object {

    public void setData(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean isAccessible = field.isAccessible();
            if(field.isAccessible()==false){
                field.setAccessible(true);
                if(field.getType().isPrimitive()){

                }
                else if( field.equals("sss") ){

                }
                else if(field.getType().isArray()){

                }


            }
            field.setAccessible(isAccessible);
        }
    }
}
