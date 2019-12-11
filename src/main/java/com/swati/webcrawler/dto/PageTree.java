package com.swati.webcrawler.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;

@Data
public class PageTree {
	
    private String url;

    private String title;
   
    private int imageCount;
    
    private List<PageTree> nodes;


    public PageTree(final String url) {
        this.url = url;
    }

    public PageTree url(final String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public PageTree title(final String title) {
        this.title = title;
        return this;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public PageTree nodes(final List<PageTree> nodes) {
        this.nodes = nodes;
        return this;
    }

    public PageTree addNodesItem(final PageTree nodesItem) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        if (nodesItem != null) {
            nodes.add(nodesItem);
        }
        return this;
    }
    public List<PageTree> getNodes() {
        return nodes;
    }

    public void setNodes(final List<PageTree> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PageTree pageTreeInfo = (PageTree) o;
        return Objects.equals(url, pageTreeInfo.url) && Objects.equals(title, pageTreeInfo.title)
                && Objects.equals(nodes, pageTreeInfo.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, nodes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class PageTreeInfo {\n");

        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    nodes: ").append(toIndentedString(nodes)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(final java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
