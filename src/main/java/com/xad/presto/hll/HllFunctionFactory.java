package com.xad.presto.hll;

import com.facebook.presto.metadata.FunctionFactory;
import com.facebook.presto.metadata.FunctionListBuilder;
import com.facebook.presto.metadata.ParametricFunction;

import java.util.List;

import com.facebook.presto.spi.type.TypeManager;
import io.airlift.log.Logger;

/**
 * Created by rbanikaz on 2015-09-16.
 */
public class HllFunctionFactory implements FunctionFactory {
    private final TypeManager typeManager;
    private static final Logger log = Logger.get(HllFunctionFactory.class);

    public HllFunctionFactory(TypeManager tm) {
        this.typeManager = tm;
    }


    @Override
    public List<ParametricFunction> listFunctions() {
        FunctionListBuilder builder = new FunctionListBuilder(typeManager);

        builder.scalar(com.xad.presto.hll.scalar.HllVersion.class);

        return builder.getFunctions();
    }
}
