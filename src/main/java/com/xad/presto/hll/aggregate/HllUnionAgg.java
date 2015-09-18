package com.xad.presto.hll.aggregate;

import com.facebook.presto.operator.aggregation.AggregationFunction;
import com.facebook.presto.operator.aggregation.CombineFunction;
import com.facebook.presto.operator.aggregation.InputFunction;
import com.facebook.presto.operator.aggregation.OutputFunction;
import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.type.StandardTypes;
import com.facebook.presto.type.SqlType;
import com.xad.presto.hll.aggregate.state.ByteArrayState;
import io.airlift.log.Logger;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import net.agkn.hll.HLL;
import net.agkn.hll.util.NumberUtil;

/**
 * Created by rbanikaz on 9/17/15.
 */
@AggregationFunction("hll_union_agg")
public final class HllUnionAgg {
    private static final Logger log = Logger.get(HllUnionAgg.class);

    // For compatibility with Postgres files, we support an optional \\x prefix on the HLL Hex Strings
    private static String OPTIONAL_HLL_PREFIX = "\\\\x";

    @InputFunction
    public static void input_varchar(ByteArrayState state, @SqlType(StandardTypes.VARCHAR) Slice value ) {
        if(value == null) return;

        String valueStr = value.toStringUtf8();

        if(valueStr.startsWith(OPTIONAL_HLL_PREFIX)) {
            valueStr = valueStr.substring(OPTIONAL_HLL_PREFIX.length());
        }

        input(state, NumberUtil.fromHex(valueStr, 0, valueStr.length()));

    }

    @InputFunction
    public static void input_varbin(ByteArrayState state, @SqlType(StandardTypes.VARBINARY) Slice value ) {
        if(value == null) return;

        input(state, value.getBytes());
    }

    public static void input(ByteArrayState state, byte[] bytes ) {

        HLL hll = HLL.fromBytes(bytes);

        if(state.getSlice() != null) {
            hll.union(HLL.fromBytes(state.getSlice().getBytes()));
        }

        state.setSlice(Slices.wrappedBuffer(hll.toBytes()));

    }

    @CombineFunction
    public static void combine(ByteArrayState state, ByteArrayState otherState) {
        Slice slice = state.getSlice();
        Slice otherSlice = otherState.getSlice();

        HLL hll = null;

        if(slice != null && otherSlice != null) {
            hll = HLL.fromBytes(slice.getBytes());
            hll.union(HLL.fromBytes(otherSlice.getBytes()));
        } else if(slice != null) {
            hll = HLL.fromBytes(slice.getBytes());
        } else if(otherSlice != null) {
            hll = HLL.fromBytes(otherSlice.getBytes());
        }

        if(hll != null) {
            state.setSlice(Slices.wrappedBuffer(hll.toBytes()));
        }


    }

    @OutputFunction(StandardTypes.VARBINARY)
    public static void output(ByteArrayState state, BlockBuilder out) {
        byte[] bytes = state.getSlice().getBytes();
        out.writeBytes(Slices.wrappedBuffer(bytes), 0, bytes.length).closeEntry();
    }

}
