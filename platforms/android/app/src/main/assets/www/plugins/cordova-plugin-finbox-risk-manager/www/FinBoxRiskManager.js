cordova.define("cordova-plugin-finbox-risk-manager.FinBoxRiskManager", function(require, exports, module) {
var exec = require('cordova/exec');

exports.createUser = function (apiKey, customerId, success, error) {
    exec(success, error, 'FinBoxRiskManager', 'createUser', [apiKey, customerId]);
};

exports.setSyncFrequency = function (duration, success, error) {
    exec(success, error, 'FinBoxRiskManager', 'setSyncFrequency', [duration]);
};

exports.startPeriodicSync = function (success, error) {
    exec(success, error, 'FinBoxRiskManager', 'startPeriodicSync', []);
};

exports.stopPeriodicSync = function (success, error) {
    exec(success, error, 'FinBoxRiskManager', 'stopPeriodicSync', []);
};

exports.resetUserData = function (success, error) {
    exec(success, error, 'FinBoxRiskManager', 'resetUserData', []);
};

exports.setDeviceMatch = function (email, fullName, phone, success, error) {
    exec(success, error, 'FinBoxRiskManager', 'setDeviceMatch', [email, fullName, phone]);
};

exports.initLibrary = function (success, error) {
    exec(success, error, 'FinBoxRiskManager', 'initLibrary', []);
};

exports.forwardFinBoxNotificationToSDK = function (data, success, error) {
    exec(success, error, 'FinBoxRiskManager', 'forwardFinBoxNotificationToSDK', [data]);
};

});
