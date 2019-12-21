package com.pp.plangenerator.functions;

/**
 * 
 * @author PRATHAMESH PAWAR
 *
 */
@FunctionalInterface
public interface PrincipleAmountFunction<T, R> {
	public R calculatePrincipleAmount(T t1, T t2, T t3);
}