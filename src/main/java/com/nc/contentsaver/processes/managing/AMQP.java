package com.nc.contentsaver.processes.managing;

import com.nc.contentsaver.processes.linking.DataLinkBufferObject;

import java.util.Deque;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

/**
 * The queue to which the content arrives for recording. Observable.
 * Subscribers will be notified when new content is available for recording.
 */
public final class AMQP extends Observable {
    /**
     * Logger.
     * Displays information about the object getting into the queue and about leaving it.
     */
    private static final Logger LOG = Logger.getLogger(AMQP.class.getSimpleName());
    /**
     * The only created queue.
     */
    private static AMQP instance = new AMQP();

    /**
     * Singleton.
     * Creates AMQP object.
     */
    private AMQP() {
    }

    /**
     * Returns a single queue object.
     *
     * @return queue object
     */
    public static AMQP getInstance() {
        return instance;
    }

    /**
     * The queue containing the objects whose data you want to save.
     */
    private Deque<DataLinkBufferObject> dataLinkObjectDeque = new ConcurrentLinkedDeque<>();

    /**
     * Queues data to be saved.
     *
     * @param data data to be saved
     */
    public void saveData(DataLinkBufferObject data) {
        dataLinkObjectDeque.addLast(data);
        LOG.info("Object entered the write queue.");
        setChanged();
        notifyObservers();
    }

    /**
     * Gets data to save from the queue.
     *
     * @return data to save
     */
    public DataLinkBufferObject getData() {
        LOG.info("The object has left the write queue.");
        return dataLinkObjectDeque.pollFirst();
    }
}
