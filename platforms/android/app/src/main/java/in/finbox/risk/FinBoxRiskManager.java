package in.finbox.risk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import in.finbox.mobileriskmanager.FinBox;
import android.content.Context;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import in.finbox.mobileriskmanager.devicematch.DeviceMatch;
import in.finbox.mobileriskmanager.notifications.MessagingService;


/**
 * FinBox Risk Manager Class
 */
public class FinBoxRiskManager extends CordovaPlugin {

    /**
     * Instance of FinBox
     */
    private final FinBox finbox = new FinBox();

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("createUser")) {
            // Read the api key
            final String apiKey = args.getString(0);
            // Read the customer id
            final String customerId = args.getString(1);
            // Call create user
            this.createUser(apiKey, customerId, callbackContext);
            return true;
        } else if (action.equals("startPeriodicSync")) {
            // Call Start Periodic Sync
            this.startPeriodicSync();
            return true;
        } else if (action.equals("setSyncFrequency")) {
            // Read the duration
            final int duration = args.getInt(0);
            // Set the sync frequency
            this.setSyncFrequency(duration);
            return true;
        } else if (action.equals("stopPeriodicSync")) {
            // Stop Periodic Sync
            this.stopPeriodicSync();
            return true;
        } else if (action.equals("resetUserData")) {
            // Reset the data
            this.resetUserData();
            return true;
        } else if (action.equals("setDeviceMatch")) {
            // Read the email
            final String email = args.getString(0);
            // Read the name
            final String name = args.getString(1);
            // Read the phone
            final String phone = args.getString(2);
            // Set Device Match
            this.setDeviceMatch(email, name, phone);
            return true;
        } else if (action.equals("initLibrary")) {
            // Get the context
            final Context context = this.cordova.getActivity().getApplicationContext();
            // Initialize the Library
            this.initLibrary(context);
            return true;
        } else if (action.equals("forwardFinBoxNotificationToSDK")) {
            // Get the context
            final Context context = this.cordova.getActivity().getApplicationContext();
            // Read the data in json format
            final JSONObject jsonData = (JSONObject) args.get(0);
            // Convert Json Object to map 
            final Map<String, String> data = new Gson().fromJson(jsonData.toString(),
                    new TypeToken<Map<String, String>>() {}.getType());
            // Parse the notification and Forward it to SDK
            this.forwardFinBoxNotificationToSDK(context, data);
            return true;
        }
        return false;
    }

    /**
     * FinBox create User function. This is used to create a new instance of the user and also to make sure all the
     * syncs are running periodically. Make sure this function is called on every app start
     *
     * @param apiKey          The Api Key that is given to authorize valid user
     * @param customerId      The name of the user for which profiling will be done
     * @param callbackContext Callback to the cordova app
     */
    private void createUser(@NonNull String apiKey, @NonNull String customerId, CallbackContext callbackContext) {
        FinBox.createUser(apiKey, customerId, new FinBox.FinBoxAuthCallback() {
            @Override
            public void onSuccess(@NonNull String s) {
                // Return success response
                callbackContext.success(s);
            }

            @Override
            public void onError(int i) {
                // Return error response
                callbackContext.error(i);
            }
        });
    }

    /**
     * Set the device match
     */
    private void setDeviceMatch(@Nullable final String email, @Nullable final String name, @Nullable final String phone) {
        // Create a device match builder
        final DeviceMatch.Builder builder = new DeviceMatch.Builder();
        if (email != null) {
            // Set the email
            builder.setEmail(email);
        }
        if (name != null) {
            // Set the name
            builder.setName(name);
        }
        if (phone != null) {
            // Set the phone number
            builder.setPhone(phone);
        }
        // Build Device Match
        final DeviceMatch deviceMatch = builder.build();
        // Set Device Match
        finbox.setDeviceMatch(deviceMatch);
    }

    /**
     * Once user is created syncs need to be started. hence call this function when createUser returns success
     */
    private void startPeriodicSync() {
        finbox.setRealTime(true);
        finbox.startPeriodicSync();
    }

    /**
     * Set Sync Frequency
     *
     * @param duration Duration in which the sync should occur in background
     */
    private void setSyncFrequency(int duration) {
        finbox.setSyncFrequency(duration);
    }

    /**
     * Function to stop all periodic syncs that are running in the background
     */
    public void stopPeriodicSync() {
        finbox.stopPeriodicSync();
    }

    /**
     * In order to reset all data and fetch all details of the user call this method
     */
    public void resetUserData() {
        FinBox.resetData();
    }

    /**
     * Initialize the Library
     */
    public void initLibrary(@NonNull final Context context) {
        FinBox.initLibrary(context);
    }

    /**
     * Check the notification received and decide whether to forward it to the 
     */
    public void forwardFinBoxNotificationToSDK(@NonNull final Context context, @NonNull final Map<String, String> data) {
        if (MessagingService.forwardToFinBoxSDK(data)) {
            // Create an instance of the messaging service
            final MessagingService firebaseMessagingService = new MessagingService(context);
            // Forward the notification to the Messaging Service
            firebaseMessagingService.onMessageReceived(data);
        } else {
            // Ignore the FCM Notification
        }
    }
}
