package com.ekta.marvel.network.response.Comics;

/**
 * Created by Ekta on 11-06-2017.
 */

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("digitalId")
    @Expose
    private Integer digitalId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("issueNumber")
    @Expose
    private Integer issueNumber;
    @SerializedName("variantDescription")
    @Expose
    private String variantDescription;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("isbn")
    @Expose
    private String isbn;
    @SerializedName("upc")
    @Expose
    private String upc;
    @SerializedName("diamondCode")
    @Expose
    private String diamondCode;
    @SerializedName("ean")
    @Expose
    private String ean;
    @SerializedName("issn")
    @Expose
    private String issn;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("textObjects")
    @Expose
    private List<Object> textObjects = null;
    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("urls")
    @Expose
    private List<Url> urls = null;
    @SerializedName("series")
    @Expose
    private Series series;
    @SerializedName("variants")
    @Expose
    private List<Object> variants = null;
    @SerializedName("collections")
    @Expose
    private List<Object> collections = null;
    @SerializedName("collectedIssues")
    @Expose
    private List<Object> collectedIssues = null;
    @SerializedName("dates")
    @Expose
    private List<Date> dates = null;
    @SerializedName("prices")
    @Expose
    private List<Price> prices = null;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("images")
    @Expose
    private List<Object> images = null;
    @SerializedName("creators")
    @Expose
    private Creators creators;
    @SerializedName("characters")
    @Expose
    private Characters characters;
    @SerializedName("stories")
    @Expose
    private Stories stories;
    @SerializedName("events")
    @Expose
    private Events events;
    public final static Parcelable.Creator<Result> CREATOR = new Creator<Result>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Result createFromParcel(Parcel in) {
            Result instance = new Result();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.digitalId = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.issueNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.variantDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.description = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.modified = ((String) in.readValue((String.class.getClassLoader())));
            instance.isbn = ((String) in.readValue((String.class.getClassLoader())));
            instance.upc = ((String) in.readValue((String.class.getClassLoader())));
            instance.diamondCode = ((String) in.readValue((String.class.getClassLoader())));
            instance.ean = ((String) in.readValue((String.class.getClassLoader())));
            instance.issn = ((String) in.readValue((String.class.getClassLoader())));
            instance.format = ((String) in.readValue((String.class.getClassLoader())));
            instance.pageCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.textObjects, (java.lang.Object.class.getClassLoader()));
            instance.resourceURI = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.urls, (com.ekta.marvel.network.response.Comics.Url.class.getClassLoader()));
            instance.series = ((Series) in.readValue((Series.class.getClassLoader())));
            in.readList(instance.variants, (java.lang.Object.class.getClassLoader()));
            in.readList(instance.collections, (java.lang.Object.class.getClassLoader()));
            in.readList(instance.collectedIssues, (java.lang.Object.class.getClassLoader()));
            in.readList(instance.dates, (com.ekta.marvel.network.response.Comics.Date.class.getClassLoader()));
            in.readList(instance.prices, (com.ekta.marvel.network.response.Comics.Price.class.getClassLoader()));
            instance.thumbnail = ((Thumbnail) in.readValue((Thumbnail.class.getClassLoader())));
            in.readList(instance.images, (java.lang.Object.class.getClassLoader()));
            instance.creators = ((Creators) in.readValue((Creators.class.getClassLoader())));
            instance.characters = ((Characters) in.readValue((Characters.class.getClassLoader())));
            instance.stories = ((Stories) in.readValue((Stories.class.getClassLoader())));
            instance.events = ((Events) in.readValue((Events.class.getClassLoader())));
            return instance;
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    }
            ;
    private final static long serialVersionUID = 6213387505917939857L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(Integer digitalId) {
        this.digitalId = digitalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getVariantDescription() {
        return variantDescription;
    }

    public void setVariantDescription(String variantDescription) {
        this.variantDescription = variantDescription;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getDiamondCode() {
        return diamondCode;
    }

    public void setDiamondCode(String diamondCode) {
        this.diamondCode = diamondCode;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<Object> getTextObjects() {
        return textObjects;
    }

    public void setTextObjects(List<Object> textObjects) {
        this.textObjects = textObjects;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public List<Object> getVariants() {
        return variants;
    }

    public void setVariants(List<Object> variants) {
        this.variants = variants;
    }

    public List<Object> getCollections() {
        return collections;
    }

    public void setCollections(List<Object> collections) {
        this.collections = collections;
    }

    public List<Object> getCollectedIssues() {
        return collectedIssues;
    }

    public void setCollectedIssues(List<Object> collectedIssues) {
        this.collectedIssues = collectedIssues;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

    public Creators getCreators() {
        return creators;
    }

    public void setCreators(Creators creators) {
        this.creators = creators;
    }

    public Characters getCharacters() {
        return characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }

    public Stories getStories() {
        return stories;
    }

    public void setStories(Stories stories) {
        this.stories = stories;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(digitalId);
        dest.writeValue(title);
        dest.writeValue(issueNumber);
        dest.writeValue(variantDescription);
        dest.writeValue(description);
        dest.writeValue(modified);
        dest.writeValue(isbn);
        dest.writeValue(upc);
        dest.writeValue(diamondCode);
        dest.writeValue(ean);
        dest.writeValue(issn);
        dest.writeValue(format);
        dest.writeValue(pageCount);
        dest.writeList(textObjects);
        dest.writeValue(resourceURI);
        dest.writeList(urls);
        dest.writeValue(series);
        dest.writeList(variants);
        dest.writeList(collections);
        dest.writeList(collectedIssues);
        dest.writeList(dates);
        dest.writeList(prices);
        dest.writeValue(thumbnail);
        dest.writeList(images);
        dest.writeValue(creators);
        dest.writeValue(characters);
        dest.writeValue(stories);
        dest.writeValue(events);
    }

    public int describeContents() {
        return 0;
    }

}
