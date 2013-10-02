/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

/**
 *
 * @author zozo
 */
public class Mediator {

    private static Map<String, CustomObservable> channels;

    private static Map<String, CustomObservable> channels() {
        if (channels == null) {
            channels = new HashMap<String, CustomObservable>();
        }
        return channels;
    }

    public static void subscribe(String eventName, Observer handler) {
        Map<String, CustomObservable> _channels = channels();
        if (channels().containsKey(eventName)) {
            CustomObservable o = _channels.get(eventName);
            o.addObserver(handler);
            _channels.put(eventName, o);
        } else {
            CustomObservable o = new CustomObservable();
            o.addObserver(handler);
            _channels.put(eventName, o);
        }
    }

    public static boolean unsubscribe(String eventName, Observer handler) {
        Map<String, CustomObservable> _channels = channels();
        if (!_channels.containsKey(eventName)) {
            return false;
        }
        CustomObservable o = _channels.get(eventName);
        o.deleteObserver(handler);
        _channels.put(eventName, o);
        return true;
    }

    public static boolean publish(String eventName, Object args) {
        Map<String, CustomObservable> _channels = channels();
        if (!_channels.containsKey(eventName)) {
            return false;
        }
        CustomObservable o = _channels.get(eventName);
        o.changed();
        o.notifyObservers(args);
        return true;
    }

    public static boolean publish(String eventName) {
        return publish(eventName, null);
    }

    public static void clearAll() {
        channels.clear();
    }
}
