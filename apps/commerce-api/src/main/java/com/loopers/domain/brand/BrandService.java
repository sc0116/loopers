package com.loopers.domain.brand;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class BrandService {

	private final BrandRepository brandRepository;

	@Transactional(readOnly = true)
	public List<BrandInfo> getBrands(final BrandCommand.GetBrands command) {
		return brandRepository.findAllBy(command.ids()).stream()
			.map(BrandInfo::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public BrandInfo getBrand(final BrandCommand.GetBrand command) {
		return brandRepository.findBy(command.id())
			.map(BrandInfo::from)
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 브랜드입니다."));
	}
}
