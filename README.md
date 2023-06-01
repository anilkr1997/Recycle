# Recycle
# Bluetooth print using Jetpack Compose and ESC/POS dependency

# Dependency
DantSu’s library makes printing to thermal printers easy with (Bluetooth, TCP, and USB) ESC/POS thermal printers. (It does not work with Star Thermal Printers)

Add the JitPack repository to your settings.gradle file
dependencyResolutionManagement {
    repositories {
        google()
        maven { url 'https://jitpack.io' }
        mavenCentral()
    }
}
# Add the dependency
dependencies {
    implementation("com.github.DantSu:ESCPOS-ThermalPrinter-Android:3.2.0")
}
# Add permissions to manifest
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
# Now we need to check if all the permissions are granted and after that let’s print a receipt

class Print(
    private val context: Context
) {
    fun print() {
        val printer = EscPosPrinter(
            BluetoothPrintersConnections.selectFirstPaired(),
            203,
            48f,
            32
        )

        val formattedText = createFormattedText(printer = printer)

        printer.printFormattedText(formattedText)
    }

    private fun createFormattedText(printer: EscPosPrinter): String {
        return "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
            printer,
            context.resources.getDrawableForDensity(
                R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM
            )
        )+"</img>\n" +
                "[L]\n" +
                "[C]<u><font size='big'>ORDER N°045</font></u>\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
                "[L]  + Size : S\n" +
                "[L]\n" +
                "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                "[L]  + Size : 57/58\n" +
                "[L]\n" +
                "[C]--------------------------------\n" +
                "[R]TOTAL PRICE :[R]34.98e\n" +
                "[R]TAX :[R]4.23e\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]<font size='tall'>Customer :</font>\n" +
                "[L]Raymond DUPONT\n" +
                "[L]5 rue des girafes\n" +
                "[L]31547 PERPETES\n" +
                "[L]Tel : +33801201456\n" +
                "[L]\n" +
                "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
                "[C]<qrcode size='20'>http://www.developpeur-web.dantsu.com/</qrcode>"
        
    }
}
Now let’s dive into Jetpack Compose
# Request Bluetooth permissions
Firstly we need to request Bluetooth permissions and we will use the Accompanist library to do that.

dependencies {
     implementation("com.google.accompanist:accompanist-permissions:0.28.0")
}
After adding the dependency we need to request Bluetooth permissions.

val bluetoothPermissions =
        // Checks if the device has Android 12 or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            rememberMultiplePermissionsState(
                permissions = listOf(
                    android.Manifest.permission.BLUETOOTH,
                    android.Manifest.permission.BLUETOOTH_ADMIN,
                    android.Manifest.permission.BLUETOOTH_CONNECT,
                    android.Manifest.permission.BLUETOOTH_SCAN,
                )
            )
        } else {
            rememberMultiplePermissionsState(
                permissions = listOf(
                    android.Manifest.permission.BLUETOOTH,
                    android.Manifest.permission.BLUETOOTH_ADMIN,
                )
            )
        }
Show an alert that let the user enable Bluetooth
If the user has Bluetooth off you can tell him to turn it on. Let’s see how to do that.

val enableBluetoothContract = rememberLauncherForActivityResult(
    ActivityResultContracts.StartActivityForResult()
) {
    if (it.resultCode == Activity.RESULT_OK) {
        Log.d("bluetoothLauncher", "Success")
        bluetoothPrint.print()
    } else {
        Log.w("bluetoothLauncher", "Failed")
    }
}

To check if the user has already granted permissions and has Bluetooth enabled we need a Bluetooth Adapter.

// This intent will open the enable bluetooth dialog
val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

val bluetoothManager = remember {
    context.getSystemService(BluetoothManager::class.java)
}
val bluetoothAdapter: BluetoothAdapter? = remember {
    bluetoothManager.adapter
}

Button(
    onClick = {
        if (bluetoothPermissions.allPermissionsGranted) {
            if (bluetoothAdapter?.isEnabled == true) {
                // Bluetooth is on print the receipt
                bluetoothPrint.print()
            } else {
                // Bluetooth is off, ask user to turn it on
                enableBluetoothContract.launch(enableBluetoothIntent)
            }
        } else {
            // Show error message
        }
    }
) {
    Text(text = "Print receipt")
}
Now we are done! Here is one of my receipts printed using this library (I bought a printer to test this and the printer was…)

I hope that you found this article helpful!


