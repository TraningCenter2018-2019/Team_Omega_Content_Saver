package com.nc.contentsaver.processes.saving;

import com.nc.contentsaver.processes.linking.DataLinkBufferObject;

import java.util.Deque;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedDeque;

public class AMQP extends Observable {
    private static AMQP instance = new AMQP();

    private AMQP() {
    }

    public static AMQP getInstance() {
        return instance;
    }

    private Deque<DataLinkBufferObject> dataLinkObjectDeque = new ConcurrentLinkedDeque<>();

    public void saveData(DataLinkBufferObject data) {
        dataLinkObjectDeque.addLast(data);
        System.out.println("Объект поступил в очередь на запись");
        setChanged();
        notifyObservers();
    }

    public DataLinkBufferObject getData() {
        System.out.println("Объект вышел из очереди на запись");
        return dataLinkObjectDeque.getFirst();
    }
}
