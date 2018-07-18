# CryptoExchange
Built with android studio and java. I have extended the capabilities of this app. Import, Study the code and Understand how things are done.

Download
    --------

    ### [Click here to download latest version from Google's Playstore](https://play.google.com/store/apps/details?id=tech.dappworld.cryptoexchange&hl=en_US)


# CryptoExchange Andela-ALC-Assessment

Andela Android Learning Community Intermediate Track Challenge; A Google/Andela Learning Community (ALC) 2.0 for Nigerians & Kenyans Assessment Project by me Olebuezi Obinna David

# Challenge

Build an Android application to show the latest exchange rate between cryptocurrencies (BTC and ETH) and 20 major world currencies including Naira. Use the crypyocompare public api to get the latest exchange rate

Users can create cards on the application screen to show the exchange rate between cryptocurrency and any of 20 major world currency including Naira. Take a look at this Clicking each card should take you to conversion screen. User can enter an amount to be converted in a base currency User should get conversion result

# Manifest Dependencies

Uses Internet permissions in AndroidManifest.xml file.

# Gradle Dependencies

compile fileTree(dir: 'libs', include: ['*.jar']) androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', { exclude group: 'com.android.support', module: 'support-annotations' }) compile 'com.android.support:appcompat-v7:26.+' compile 'com.android.support.constraint:constraint-layout:1.0.2' testCompile 'junit:junit:4.12'

    
     compile fileTree(include: ['*.jar'], dir: 'libs')
        androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
            exclude group: 'com.android.support', module: 'support-annotations'
        })
        compile 'com.android.support:appcompat-v7:26.+'
        compile 'com.android.support.constraint:constraint-layout:1.0.2'
        testCompile 'junit:junit:4.12'

        //Add Library
        compile 'com.android.support:cardview-v7:26.+'
        compile 'com.android.support:support-v4:26.+'

        //material Spinner
        compile 'com.jaredrummler:material-spinner:1.1.0'

        //Retrofit + Picassa
        compile 'com.squareup.retrofit2:retrofit:2.3.0'
        compile 'com.squareup.retrofit2:converter-gson:2.3.0'
        compile 'com.squareup.picasso:picasso:2.5.2'

        // RecyclerView
        compile 'com.android.support:recyclerview-v7:26.+'
        compile 'com.android.support:design:26.+'

        // Glide
        compile 'com.github.bumptech.glide:glide:3.7.0'
        implementation 'com.github.rey5137:material:1.2.4'

        //
        compile 'org.jbundle.util.osgi.wrapped:org.jbundle.util.osgi.wrapped.org.apache.http.client:4.1.2'
        compile 'com.android.volley:volley:1.0.0'
        compile 'com.facebook.fresco:fresco:0.6.1'
        testCompile 'junit:junit:4.12'


    #ScreenShots

    [![mutt light](https://lh3.googleusercontent.com/EXG5g879XKsZnJL4dleSNuJwEpnX0s1-ntzJJNqK8LOnh6wB6lnKhvc4LOI2Q3SEeBaQ=w720-h310-rw)](https://lh3.googleusercontent.com/EXG5g879XKsZnJL4dleSNuJwEpnX0s1-ntzJJNqK8LOnh6wB6lnKhvc4LOI2Q3SEeBaQ=w720-h310-rw)
    [![mutt light](https://lh3.googleusercontent.com/u8qkfzAIme7uFtZaUzxXccQlP1V8x2gL4Wl9AJ3JPeIbMAm59CIUuAZ1IefDMNo2Lrak=w720-h310-rw)](https://lh3.googleusercontent.com/u8qkfzAIme7uFtZaUzxXccQlP1V8x2gL4Wl9AJ3JPeIbMAm59CIUuAZ1IefDMNo2Lrak=w720-h310-rw)
    [![mutt light](https://lh3.googleusercontent.com/XKHBiMVtmBxyAVkYllk5qC8RRlJQ2NyOxt3dwTJmdHATuS9uZMdGIyVMgvVA7rwS_yU=w720-h310-rw)](https://lh3.googleusercontent.com/XKHBiMVtmBxyAVkYllk5qC8RRlJQ2NyOxt3dwTJmdHATuS9uZMdGIyVMgvVA7rwS_yU=w720-h310-rw)
    [![mutt light](https://lh3.googleusercontent.com/EXG5g879XKsZnJL4dleSNuJwEpnX0s1-ntzJJNqK8LOnh6wB6lnKhvc4LOI2Q3SEeBaQ=w720-h310-rw)](https://lh3.googleusercontent.com/EXG5g879XKsZnJL4dleSNuJwEpnX0s1-ntzJJNqK8LOnh6wB6lnKhvc4LOI2Q3SEeBaQ=w720-h310-rw)