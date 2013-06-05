package edu.msu.frib.scanserver.api;

import edu.msu.frib.scanserver.common.XmlData;
import edu.msu.frib.scanserver.common.XmlValues;
import org.epics.util.time.Timestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Data {
    private final List<String> names;
    private final List<Class<?>> types;
    private final List<Object> values;

    protected static abstract class Builder<T extends Builder<T>> {
        protected abstract T self();

        private List<String> names = new ArrayList<String>();
        private List<Class<?>> types = new ArrayList<Class<?>>();
        private List<Object> values = new ArrayList<Object>();

        public T names(List<String> names){
            this.names = names;
            return self();
        }

        public T types(List<Class<?>> types){
            this.types = types;
            return self();
        }

        public T values(List<Object> values){
            this.values = values;
            return self();
        }

        public T fromXml (XmlData xmlData){
            List<Timestamp> dates = new ArrayList<Timestamp>();
            this.names.add("timestamp");
            this.names.addAll(xmlData.getXmlDeviceList());
            this.types.add(Timestamp.class);
            int numberColumns = xmlData.getXmlDeviceList().size();
            for (int i = -1; i<numberColumns; i++){
                List<Number> numbers = new ArrayList<Number>();
                this.types.add(Float.class);
                for (XmlValues xmlValues : xmlData.getSamples().getXmlValues()){
                    if (i==-1){
                        dates.add(xmlValues.getTime());
                    }else{
                        numbers.add(xmlValues.getValueList().get(i));
                    }
                }
                if(i==-1){
                    this.values.add(dates);
                }else{
                    this.values.add(numbers);
                }
            }
            return self();
        }

        public Data build(){
            return new Data(this);
        }
    }

    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    private Data(Builder<?> builder) {
        this.names = builder.names;
        this.types = builder.types;
        this.values = builder.values;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Class<?>> getTypes() {
        return types;
    }

    public List<Object> getValues() {
        return values;
    }
}
