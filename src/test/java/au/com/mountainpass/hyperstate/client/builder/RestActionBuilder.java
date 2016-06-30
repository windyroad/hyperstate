package au.com.mountainpass.hyperstate.client.builder;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import au.com.mountainpass.hyperstate.client.CreateAction;
import au.com.mountainpass.hyperstate.client.DeleteAction;
import au.com.mountainpass.hyperstate.client.GetAction;
import au.com.mountainpass.hyperstate.client.RestLink;
import au.com.mountainpass.hyperstate.client.UpdateAction;
import au.com.mountainpass.hyperstate.core.Action;
import au.com.mountainpass.hyperstate.core.Parameter;
import au.com.mountainpass.hyperstate.core.Resolver;

public class RestActionBuilder {

  private String identifier;
  private HttpMethod method;
  private URI href;
  private Parameter[] fields = {};

  private Resolver resolver;

  @JsonCreator
  public RestActionBuilder(@JacksonInject Resolver resolver) {
    this.resolver = resolver;
  }

  @PostConstruct
  public void postConstruct() {

  }

  @JsonProperty("method")
  public RestActionBuilder setMethod(HttpMethod method) {
    this.method = method;
    return this;
  }

  @JsonProperty("name")
  public RestActionBuilder setName(String identifier) {
    this.identifier = identifier;
    return this;
  }

  @JsonProperty("href")
  public RestActionBuilder setHref(URI href) {
    this.href = href;
    return this;
  }

  @JsonProperty("fields")
  public RestActionBuilder setFields(Parameter[] fields) {
    this.fields = fields;
    return this;
  }

  public Action<?> build() {
    switch (method) {
    case POST:
      return new CreateAction(resolver, identifier, new RestLink(href), fields);
    case DELETE:
      return new DeleteAction(resolver, identifier, new RestLink(href), fields);
    case GET:
      return new GetAction(resolver, identifier, new RestLink(href), fields);
    case PUT:
      return new UpdateAction(resolver, identifier, new RestLink(href), fields);
    default:
      return null;
    }
  }
}
