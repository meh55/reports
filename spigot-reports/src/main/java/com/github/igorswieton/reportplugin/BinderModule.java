package com.github.igorswieton.reportplugin;

import com.github.igorswieton.reportplugin.annotation.Database;
import com.github.igorswieton.reportplugin.annotation.Host;
import com.github.igorswieton.reportplugin.annotation.Password;
import com.github.igorswieton.reportplugin.annotation.Port;
import com.github.igorswieton.reportplugin.annotation.Username;
import com.github.igorswieton.reportplugin.report.MySqlReportRepository;
import com.github.igorswieton.reportplugin.report.ReportRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author Igor Swieton (https://www.github.com/igorswieton)
 * @since 24.06.2019
 */
public class BinderModule extends AbstractModule {

  private final ReportPlugin plugin;
  private final String username;
  private final Integer port;
  private final String database;
  private final String host;
  private final String password;

  @Inject
  BinderModule(ReportPlugin plugin) {
    this.plugin = plugin;
    this.username = plugin.getConfig().getString("MySQL.Username");
    this.port = plugin.getConfig().getInt("MySQL.Port");
    this.database = plugin.getConfig().getString("MySQL.Database");
    this.host = plugin.getConfig().getString("MySQL.Host");
    this.password = plugin.getConfig().getString("MySQL.Password");
  }

  Injector createInjector() {
    return Guice.createInjector(this);
  }

  @Override
  protected void configure() {
    this.bind(ReportPlugin.class).toInstance(this.plugin);
    this.bind(ReportRepository.class).to(MySqlReportRepository.class);
    this.bind(MySqlDataSourceConfiguration.class);
    this.bind(String.class).annotatedWith(Username.class).toInstance(username);
    this.bind(Integer.class).annotatedWith(Port.class).toInstance(port);
    this.bind(String.class).annotatedWith(Database.class).toInstance(database);
    this.bind(String.class).annotatedWith(Host.class).toInstance(host);
    this.bind(String.class).annotatedWith(Password.class).toInstance(password);
  }
}
