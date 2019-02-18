package com.nc.contentsaver.verticles.put.handlers;

import com.nc.contentsaver.processes.linking.DataLinkBufferObject;
import com.nc.contentsaver.processes.linking.DataLinkObject;
import com.nc.contentsaver.processes.saving.saver.DataSaverBuilder;
import com.nc.contentsaver.utils.Hasher;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;


public class PutSaveDataHandler implements Handler<Void> {
    private Buffer data;
    private DataLinkObject link;
    private HttpServerRequest request;

    public PutSaveDataHandler(Buffer data, DataLinkObject link, HttpServerRequest request) {
        this.data = data;
        this.link = link;
        this.request = request;
    }

    @Override
    public void handle(Void event) {
        request.response().end();
        request.connection().close();
        this.link.setSha256(Hasher.getSha256(data));
        System.out.println("Сохраняем data: " + data.toString());//todo: отсюда в очередь на сохранение
        DataLinkBufferObject toSave = new DataLinkBufferObject(link, data);
        new DataSaverBuilder().buildDefault().saveData(toSave);
    }
}
