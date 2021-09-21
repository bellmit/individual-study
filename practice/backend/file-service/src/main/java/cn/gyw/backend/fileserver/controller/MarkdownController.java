package cn.gyw.backend.fileserver.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.gyw.backend.fileserver.model.MdTreeNode;
import cn.gyw.backend.fileserver.model.ServiceProperties;

@RestController
@RequestMapping("/md")
public class MarkdownController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarkdownController.class);

	@Autowired
	private ServiceProperties serviceProperties;

	/**
	 * 查询所有markdown 文件
	 * 
	 * @return
	 */
	@GetMapping
	public List<MdTreeNode> queryAllMds() {
		Path rootPath = Paths.get(serviceProperties.getMdDiskDir());
		LOGGER.info("Root md path :{}", rootPath);
		Map<String, MdTreeNode> cacheMap = new HashMap<>();
		List<MdTreeNode> dataList = new ArrayList<>();
		try {
			Stream<Path> stream = Files.walk(rootPath).filter((file) -> {
				return file.toString().endsWith(".md");
			});
			
			stream.forEach((path) -> {
				Path p = path.getParent();
				if (p.equals(rootPath)) {
					dataList.add(new MdTreeNode(path.getFileName().toString()));
					return ;
				}
				
				String parentFileName = p.getFileName().toString();
				if (!cacheMap.containsKey(parentFileName)) {
					cacheMap.put(parentFileName, new MdTreeNode(parentFileName));
				}
				cacheMap.get(parentFileName).addNode(new MdTreeNode(path.getFileName().toString()));
			});
			dataList.addAll(cacheMap.values());
			return dataList;
		} catch (IOException e) {
			LOGGER.error("", e);
		}
		return Collections.emptyList();
	}

}
