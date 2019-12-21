package com.pp.plangenerator.functions;

/**
 * 
 * @author PRATHAMESH PAWAR
 *
 */
@FunctionalInterface
public interface PlanGeneratorResultFunction<T, R> {
	R setPlanGeneratorResult(T t1, T t2, T t3, T t4);
}