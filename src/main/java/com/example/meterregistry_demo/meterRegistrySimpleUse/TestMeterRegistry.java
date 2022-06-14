package com.example.meterregistry_demo.meterRegistrySimpleUse;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * @author : pan.han@okg.com
 * @date : 2022-06-14 23:04
 */

/**
    MeterRegistry 在 Micrometer 是一个抽象类，主要实现包括：
        1.SimpleMeterRegistry：每个Meter的最新数据可以收集到SimpleMeterRegistry实例中，但是这些数据不会发布到其他系统，
            也就是数据是位于应用的内存中的。
        2.CompositeMeterRegistry：多个MeterRegistry聚合，内部维护了一个MeterRegistry的列表。
        3.全局的MeterRegistry：工厂类io.micrometer.core.instrument.Metrics 中持有一个静态final的
            CompositeMeterRegistry 实例 globalRegistry。
 */
public class TestMeterRegistry {

    public static void main(String[] args) {

    }

    public static void useSimpleMeterRegistry(){
        MeterRegistry registry = new SimpleMeterRegistry();
        final Counter counter = registry.counter("counter");
        counter.increment();
    }

    public static void useCompositeMeterRegistry(){
        //CompositeMeterRegistry实例初始化的时候，内部持有的MeterRegistry列表是空的，
        //如果此时用它新增一个Meter实例，Meter实例的操作是无效的
        CompositeMeterRegistry composite = new CompositeMeterRegistry();
        final Counter compositeCounter = composite.counter("counter");
        compositeCounter.increment();// <- 实际上这一步操作是无效的，但是不会报错

        SimpleMeterRegistry simpleMeterRegistry = new SimpleMeterRegistry();
        composite.add(simpleMeterRegistry);

        compositeCounter.increment(); //计数成功
    }


}
