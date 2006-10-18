/**
 * Copyright 2006 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.solr.request;

import java.io.Writer;
import java.io.IOException;

import org.apache.solr.util.NamedList;

/**
 * Implementations of <code>QueryResponseWriter</code> are used to format responses to query requests.
 *
 * Different <code>QueryResponseWriter</code>s are registered with the <code>SolrCore</code>.
 * One way to register a QueryResponseWriter with the core is thorugh the <code>solrconfig.xml</code> file.
 * <p>
 * Example <code>solrconfig.xml</code> entry to register a <code>QueryResponseWRiter</code> implementation to
 * handle all queries with a writer type of "simple":
 * <p>
 * <code>
 *    &lt;queryResponseWriter name="simple" class="foo.SimpleResponseWriter" /&gt;
 * </code>
 * <p>
 * A single instance of any registered QueryResponseWriter is created
 * via the default constructor and is reused for all relevant queries.
 *
 * @author yonik
 * @version $Id$
 */
public interface QueryResponseWriter {
  public static String CONTENT_TYPE_XML_UTF8="text/xml; charset=UTF-8";
  public static String CONTENT_TYPE_TEXT_UTF8="text/plain; charset=UTF-8";
  public static String CONTENT_TYPE_TEXT_ASCII="text/plain; charset=US-ASCII";

  /**
   * Write a SolrQueryResponse, this method must be thread save.
   *
   * <p>
   * Information about the request (in particular: formating options) may be 
   * obtained from <code>req</code> but the dominant source of information 
   * should be <code>rsp</code>.
   * <p>
   * There are no mandatory actions that write must perform.
   * An empty write implementation would fulfill
   * all interface obligations.
   * </p> 
   */
  public void write(Writer writer, SolrQueryRequest request, SolrQueryResponse response) throws IOException;

  /** 
   * Return the applicable Content Type for a request, this method 
   * must be thread safe.
   *
   * <p>
   * QueryResponseWriter's must implement this method to return a valid 
   * HTTP Content-Type header for the request, that will logically 
   * corrispond with the output produced by the write method.
   * </p>
   * @return a Content-Type string, which may not be null.
   */
  public String getContentType(SolrQueryRequest request, SolrQueryResponse response);
  
  /** <code>init</code> will be called just once, immediately after creation.
   * <p>The args are user-level initialization parameters that
   * may be specified when declaring a response writer in
   * solrconfig.xml
   */
  public void init(NamedList args);
}

