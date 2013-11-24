package com.service;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-24
 * Time: обнГ4:35
 * To change this template use File | Settings | File Templates.
 */
public interface GetMMSFromDB {
    void queryMMSList() throws InterruptedException;

    void updateFlag(Long idnum);
}
