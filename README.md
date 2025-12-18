# Candidate Instructions & Known Bugs (do not change this file name)

This project is intentionally flawed. Your tasks:

1. Fix crashes and freezes
2. Implement navigation to the detail screen
3. Make network calls off the main thread
4. Replace GlobalScope usage with proper ViewModelScope or lifecycle-aware scope
5. Implement local persistence (Room) and use it as cache
6. Correct JSON parsing / DTO mapping
7. Add loading/error/empty states properly
8. Remove memory leak (do not use static Activity references)
9. Implement updateArticle to persist edits
10. Improve project structure where obvious (move business logic out of composables)


Deliverables:
- Working app (no crashes) that loads articles (from the fake API or a real one)
- README describing changes and tradeoffs
- Optional: unit tests and small demo video


Notes:
- Some code intentionally causes the app to freeze or crash. Find and fix them.
- We expect you to explain each bug and your fix in the README.


========================  My Submission ==================================
Github Link = https://github.com/harshitsharma25/BrokenAndroidComposeProject

Time Taken = 9 - 10 hours

Major Bugs / Problems Identified = 
1.Wrong parameters of data class of api call causes mismatch b/w the data types.
2. Repository declared as the Object and it should be Class.
3. NPE (null pointer exception error) was coming on the first run of the app  Error = " java.lang.NullPointerException: Attempt to invoke interface method 'int java.lang.CharSequence.length()' on a null object reference" because value is coming null from the Fake api as previously mentioned in assignment.
4.Badly structured whole Assignment and packages are not aligned properly.

Fixes Implemented :
1. Fixed all the bugs and problems as mentioned above while following the industry standard MVVM Architecture.
2. Made 3 Screens ( Splash Screen , Home Screen , Details Screen) and Viewmodel to keep away the Ui from the state.
3. Uses Room DB to storing the data coming from the Api and than populating the data directly from the Room DB.
4. Applied Cache such that after 30 minutes they will get refreshed via Api call.
5. Structured all the packages properly following the MVVM Architecture.
6. Fixed all the bugs and freezes.

Improvements made : 
1. Added the Logo for the app to give proper polish.
2. Added the splash screen at the starting of the app.
3. Made the Attractive User Interface of whole app.
4. Added button for the "Share" so that user can share any particular Article via the different apps using Intent.
5. Applied the Shimmer Screen via the proper state handling using the sealed class for Loading, Error and data.
