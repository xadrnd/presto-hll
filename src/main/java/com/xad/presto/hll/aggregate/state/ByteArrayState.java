package com.xad.presto.hll.aggregate.state;

import com.facebook.presto.operator.aggregation.state.AccumulatorState;
import io.airlift.slice.Slice;

/**
 * Created by rbanikaz on 9/17/15.
 */
public interface ByteArrayState extends AccumulatorState {
    public Slice getSlice();
    public void setSlice(Slice bytes);

}
