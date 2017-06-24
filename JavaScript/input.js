const repl = require('repl');

repl.start({
	prompt: "숫자 입력> ",
	eval: (command, context, filename, callback) => {
		// 입력(command)을 받았을 때 처리를 수행합니다.
		let number = Number(command);

		// 입력이 숫자인지 확인
		if (isNaN(number)) {
			console.log("숫자가 아닙니다.");
		} else {
			console.log("숫자 입니다.");
		}

		// 처리 완료
		callback();
	}
})