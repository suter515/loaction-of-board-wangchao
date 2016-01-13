cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/cordova-plugin-whitelist/whitelist.js",
        "id": "cordova-plugin-whitelist.whitelist",
        "pluginId": "cordova-plugin-whitelist",
        "runs": true
    },
//    {
//        "file": "plugins/cordova-plugin-battery-status/www/battery.js",
//        "id": "cordova-plugin-battery-status.battery",
//        "pluginId": "cordova-plugin-battery-status",
//        "clobbers": [
//            "navigator.battery"
//        ]
//    },
    {
        "file": "plugins/cordova-plugin-camera/www/CameraConstants.js",
        "id": "cordova-plugin-camera.Camera",
        "pluginId": "cordova-plugin-camera",
        "clobbers": [
            "Camera"
        ]
    },
    {
        "file": "plugins/cordova-plugin-camera/www/CameraPopoverOptions.js",
        "id": "cordova-plugin-camera.CameraPopoverOptions",
        "pluginId": "cordova-plugin-camera",
        "clobbers": [
            "CameraPopoverOptions"
        ]
    },
    {
        "file": "plugins/cordova-plugin-camera/www/Camera.js",
        "id": "cordova-plugin-camera.camera",
        "pluginId": "cordova-plugin-camera",
        "clobbers": [
            "navigator.camera"
        ]
    },
    {
        "file": "plugins/cordova-plugin-camera/www/CameraPopoverHandle.js",
        "id": "cordova-plugin-camera.CameraPopoverHandle",
        "pluginId": "cordova-plugin-camera",
        "clobbers": [
            "CameraPopoverHandle"
        ]
    },
//    {
//        "file": "plugins/cordova-plugin-device/www/device.js",
//        "id": "cordova-plugin-device.device",
//        "pluginId": "cordova-plugin-device",
//        "clobbers": [
//            "device"
//        ]
//    },
    {
        "file": "plugins/cordova-plugin-dialogs/www/notification.js",
        "id": "cordova-plugin-dialogs.notification",
        "pluginId": "cordova-plugin-dialogs",
        "merges": [
            "navigator.notification"
        ]
    },
    {
        "file": "plugins/cordova-plugin-dialogs/www/android/notification.js",
        "id": "cordova-plugin-dialogs.notification_android",
        "pluginId": "cordova-plugin-dialogs",
        "merges": [
            "navigator.notification"
        ]
    },
//    {
//        "file": "plugins/cordova-plugin-network-information/www/network.js",
//        "id": "cordova-plugin-network-information.network",
//        "pluginId": "cordova-plugin-network-information",
//        "clobbers": [
//            "navigator.connection",
//            "navigator.network.connection"
//        ]
//    },
//    {
//        "file": "plugins/cordova-plugin-network-information/www/Connection.js",
//        "id": "cordova-plugin-network-information.Connection",
//        "pluginId": "cordova-plugin-network-information",
//        "clobbers": [
//            "Connection"
//        ]
//    },
    {
        "file": "plugins/cordova-plugin-vibration/www/vibration.js",
        "id": "cordova-plugin-vibration.notification",
        "pluginId": "cordova-plugin-vibration",
        "merges": [
            "navigator.notification",
            "navigator"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.2.0",
    "cordova-plugin-battery-status": "1.1.1",
    "cordova-plugin-camera": "1.2.0",
    "cordova-plugin-device": "1.1.0",
    "cordova-plugin-dialogs": "1.2.0",
    "cordova-plugin-network-information": "1.1.0",
    "cordova-plugin-vibration": "2.0.0"
}
// BOTTOM OF METADATA
});