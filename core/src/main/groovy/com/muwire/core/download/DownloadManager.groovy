package com.muwire.core.download

import com.muwire.core.connection.I2PConnector
import com.muwire.core.files.FileDownloadedEvent

import net.i2p.data.Base64
import net.i2p.data.Destination
import net.i2p.util.ConcurrentHashSet

import com.muwire.core.EventBus
import com.muwire.core.Persona
import com.muwire.core.UILoadedEvent

import java.util.concurrent.Executor
import java.util.concurrent.Executors

public class DownloadManager {
    
    private final EventBus eventBus
    private final I2PConnector connector
    private final Executor executor
    private final File incompletes
    private final Persona me
    
    private final Set<Downloader> downloaders = new ConcurrentHashSet<>()
    
    public DownloadManager(EventBus eventBus, I2PConnector connector, File incompletes, Persona me) {
        this.eventBus = eventBus
        this.connector = connector
        this.incompletes = incompletes
        this.me = me
        
        incompletes.mkdir()
        
        this.executor = Executors.newCachedThreadPool({ r ->
            Thread rv = new Thread(r)
            rv.setName("download-worker")
            rv.setDaemon(true)
            rv
        })
    }
    
    
    public void onUIDownloadEvent(UIDownloadEvent e) {
        
        def size = e.result[0].size
        def infohash = e.result[0].infohash
        def pieceSize = e.result[0].pieceSize
        
        Set<Destination> destinations = new HashSet<>()
        e.result.each { 
            destinations.add(it.sender.destination)
        }
        
        def downloader = new Downloader(eventBus, this, me, e.target, size,
            infohash, pieceSize, connector, destinations,
            incompletes)
        downloaders.add(downloader)
        persistDownloaders()
        executor.execute({downloader.download()} as Runnable)
        eventBus.publish(new DownloadStartedEvent(downloader : downloader))
    }
    
    void resume(Downloader downloader) {
        executor.execute({downloader.download() as Runnable})
    }
    
    void onUILoadedEvent(UILoadedEvent e) {
        // TODO: load downloads here
    }
    
    void onFileDownloadedEvent(FileDownloadedEvent e) {
        downloaders.remove(e.downloader)
        persistDownloaders()
    }
    private void persistDownloaders() {
        
    }
}
