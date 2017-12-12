package edu.web.convert;

import edu.web.ParamsConvertHandler;

import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ConvertHandlerChain {

    private Iterator<ParamsConvertHandler> iterator;

    public ConvertHandlerChain(){
        iterator = ServiceLoader.load(ParamsConvertHandler.class).iterator();
    }

    public Object execute(Parameter parameter){
        Object value = null;
        if(iterator.hasNext()){
            value = iterator.next().handle(parameter, this);
        }
        return value;
    }
}
