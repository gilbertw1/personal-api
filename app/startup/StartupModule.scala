package startup

import com.google.inject.AbstractModule

class StartupModule extends AbstractModule {
  override def configure() = {
    bind(classOf[StartupLoader]).asEagerSingleton()
  }
}
