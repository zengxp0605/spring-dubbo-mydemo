package com.stan.dubbobaseinterface.service;

public interface WebEntryService {
    String entry(String groupName, String methodName, String paramId);

    /**
     *  泛化调用入口
     * @param groupName
     * @param methodName
     * @param paramId
     * @return
     */
    String entryGeneric(String groupName, String methodName, String paramId);
}
