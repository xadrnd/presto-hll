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

        // scalar
        builder.scalar(com.xad.presto.hll.scalar.HllVersion.class);
        builder.scalar(com.xad.presto.hll.scalar.HllCardinality.class);

        // aggregates
        builder.aggregate(com.xad.presto.hll.aggregate.TestAgg.class);
        builder.aggregate(com.xad.presto.hll.aggregate.HllUnionAgg.class);

        return builder.getFunctions();
    }
}
