package com.xad.presto.hll.scalar;

import com.facebook.presto.operator.Description;
import com.facebook.presto.operator.scalar.ScalarFunction;
import com.facebook.presto.spi.type.StandardTypes;
import com.facebook.presto.type.SqlType;
import io.airlift.slice.Slice;
import net.agkn.hll.HLL;

import javax.annotation.Nullable;

/**
 * Created by rbanikaz on 9/17/15.
 */
public class HllCardinality {


    @ScalarFunction("hll_cardinality")
    @Description("Returns the cardinality of an hll object")
    @SqlType(StandardTypes.BIGINT)
    public static long hll_cardinality(@Nullable @SqlType(StandardTypes.VARBINARY) Slice slice) {
        HLL hll = HLL.fromBytes(slice.getBytes());
        return hll.cardinality();
    }
}
