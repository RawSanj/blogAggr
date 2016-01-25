package com.rawsanj.blogaggr.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rawsanj.blogaggr.domain.Item;
import com.rawsanj.blogaggr.exception.RssException;
import com.rawsanj.blogaggr.rss.ObjectFactory;
import com.rawsanj.blogaggr.rss.TRss;
import com.rawsanj.blogaggr.rss.TRssChannel;
import com.rawsanj.blogaggr.rss.TRssItem;

@Service
public class RssService {
	
	private final Logger log = LoggerFactory.getLogger(MailService.class);
	
	public List<Item> getItems(File file) throws JAXBException{
		return getItems(new StreamSource(file));
	}

	public List<Item> getItems(String url) throws JAXBException{
		return getItems(new StreamSource(url));
	}

	private List<Item> getItems(Source source) throws JAXBException{
		ArrayList<Item> list = new ArrayList<Item>();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<TRss> jaxbElement = unmarshaller.unmarshal(source, TRss.class);
			TRss rss = jaxbElement.getValue();
			
			List<TRssChannel> channels = rss.getChannel();
			for (TRssChannel channel : channels) {
				List<TRssItem> items = channel.getItem();
				for (TRssItem rssItem : items) {
					Item item = new Item();
					item.setTitle(rssItem.getTitle());
					item.setDescription(rssItem.getDescription());
					item.setLink(rssItem.getLink());
					//log.debug("DATE -> " +rssItem.getPubDate());
					DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
					ZonedDateTime dateTime = ZonedDateTime.parse(rssItem.getPubDate(), formatter);
					
					item.setPublishedDate(dateTime);
					list.add(item);
				}
			}
		} catch (JAXBException  e) {
			log.warn("Exception in retrieveing Items for '{}', exception is: {}", source, e.getMessage());
		}
		return list;
	}
}
