package com.loopers.domain.brand;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class BrandService {

	private final BrandRepository brandRepository;

	@Transactional(readOnly = true)
	public BrandInfo get(final BrandCommand.Get command) {
		return brandRepository.findById(command.id())
			.map(BrandInfo::from)
			.orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 브랜드입니다."));
	}
}
