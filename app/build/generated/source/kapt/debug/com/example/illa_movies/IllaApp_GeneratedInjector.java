package com.example.illa_movies;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = IllaApp.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface IllaApp_GeneratedInjector {
  void injectIllaApp(IllaApp illaApp);
}
