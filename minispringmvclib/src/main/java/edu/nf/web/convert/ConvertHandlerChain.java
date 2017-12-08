package edu.nf.web.convert;

import edu.nf.web.ParamInfo;
import edu.nf.web.ParamsConvertHandler;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ConvertHandlerChain {

    private Iterator<ParamsConvertHandler> iterator;

    public ConvertHandlerChain(){
        iterator = ServiceLoader.load(ParamsConvertHandler.class).iterator();
    }

    public Object execute(ParamInfo paramInfo){
        Object value = null;
        if(iterator.hasNext()){
            value = iterator.next().handle(paramInfo, this);
        }
        return value;
    }
}
