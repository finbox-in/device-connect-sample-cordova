cordova.define('cordova/plugin_list', function(require, exports, module) {
  module.exports = [
    {
      "id": "cordova-plugin-finbox-risk-manager.FinBoxRiskManager",
      "file": "plugins/cordova-plugin-finbox-risk-manager/www/FinBoxRiskManager.js",
      "pluginId": "cordova-plugin-finbox-risk-manager",
      "clobbers": [
        "cordova.plugins.FinBoxRiskManager"
      ]
    },
    {
      "id": "cordova-plugin-firebase-messaging.FirebaseMessaging",
      "file": "plugins/cordova-plugin-firebase-messaging/www/FirebaseMessaging.js",
      "pluginId": "cordova-plugin-firebase-messaging",
      "merges": [
        "cordova.plugins.firebase.messaging"
      ]
    }
  ];
  module.exports.metadata = {
    "cordova-plugin-finbox-risk-manager": "0.1.0",
    "cordova-support-android-plugin": "2.0.4",
    "cordova-plugin-firebase-messaging": "8.0.1"
  };
});