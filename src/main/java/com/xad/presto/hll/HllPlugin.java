package com.xad.presto.hll;

import com.facebook.presto.metadata.FunctionFactory;
import com.facebook.presto.spi.Plugin;
import com.facebook.presto.spi.type.TypeManager;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rbanikaz on 9/16/15.
 */
public class HllPlugin implements Plugin {

    TypeManager typeManager;

    @Inject
    public void setTypeManager(TypeManager typeManager)
    {
        this.typeManager = checkNotNull(typeManager, "typeManager is null");
    }

    @Override
    public void setOptionalConfig(Map<String, String> stringStringMap) {

    }

    @Override
    public <T> List<T> getServices(Class<T> tClass) {
        if (tClass == FunctionFactory.class) {
            return ImmutableList.of(tClass.cast(new HllFunctionFactory(typeManager)));
        }

        return ImmutableList.of();
    }
}
