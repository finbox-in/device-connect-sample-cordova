/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready
document.addEventListener('deviceready', onDeviceReady, false);

const API_KEY = 'I9G9Js79et7ykyLCnFp279XxsJH85Jpu3d5E2Log';
const CUSTOMER_ID = 'demo_lender_2411355';

function onDeviceReady() {
    // Cordova is now initialized. Have fun!

    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');

    // FCM intergration
    cordova.plugins.firebase.messaging.onMessage(function (payload) {
        console.log("FCM message Foreground: " + JSON.stringify(payload));
        cordova.plugins.FinBoxRiskManager.forwardFinBoxNotificationToSDK(payload);
    });

    cordova.plugins.firebase.messaging.onBackgroundMessage(function (payload) {
        console.log("FCM message Background: " + JSON.stringify(payload));
        cordova.plugins.FinBoxRiskManager.forwardFinBoxNotificationToSDK(payload);
    });

    cordova.plugins.firebase.messaging.getToken().then(function (token) {
        console.log("Got device token: " + token);
    });

    // Get the UI elements
    const createButton = document.getElementById("createButton")
    const outputDiv = document.getElementById("outputDiv")

    // Add event listener to button
    createButton.addEventListener('click', function () {
        // User Created
        outputDiv.textContent = "Button Clicked";
        console.log('Create button clicked');
        createUser()
    });
}

function createUser() {
    console.log('Create User')
    cordova.plugins.FinBoxRiskManager.createUser(API_KEY, CUSTOMER_ID, function (response) {
        console.log('Access Token: ' + response);
        startSync();
    }, function (error) {
        console.log('Create User Error: ' + error);
    });
}

function startSync() {
    console.log('Start Sync')
    cordova.plugins.FinBoxRiskManager.startPeriodicSync();
}