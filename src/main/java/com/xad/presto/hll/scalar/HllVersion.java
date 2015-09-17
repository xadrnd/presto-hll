package com.xad.presto.hll.scalar;

import com.facebook.presto.operator.Description;
import com.facebook.presto.operator.scalar.ScalarFunction;
import com.facebook.presto.spi.type.StandardTypes;
import com.facebook.presto.type.SqlType;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;


/**
 * Created by rbanikaz on 2015-09-06
 */
public class HllVersion {

    private static final String UDF_VERSION = "v0.0.1";

    @ScalarFunction("hll_udf_version")
    @Description("Returns the version of the HLL UDF, used to make sure UDFs are installed properly")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice hll_udf_version() {
        return Slices.utf8Slice(UDF_VERSION);
    }
}
